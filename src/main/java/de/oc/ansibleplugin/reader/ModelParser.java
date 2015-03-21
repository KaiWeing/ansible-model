package de.oc.ansibleplugin.reader;

import de.oc.ansibleplugin.model.AnsibleModule;
import de.oc.ansibleplugin.model.AnsibleModuleOption;
import de.oc.ansibleplugin.model.AnsibleModules;
import de.oc.ansibleplugin.model.AnsibleVersion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parses the Module-Descriptions from the Ansible docs Webpage.
 *
 * Created by Weingärtner on 21.03.2015.
 */
public class ModelParser {

    private static final Pattern MODULE_VERSION_PATTERN = Pattern.compile("New in version (\\d\\.\\d).");
    private static final Pattern OPTION_VERSION_PATTERN = Pattern.compile(".*\\(added in Ansible (\\d\\.\\d)\\)");

    public ModelParser() {

    }

    private Element getBodyFrom(String relativePath) {
        String url = "http://docs.ansible.com/" + relativePath;
        try {
            return Jsoup.connect(url).get().body();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get page " + url);
        }
    }

    public AnsibleModules readModules(String modulesPath) {
        List<AnsibleModule> modules = getBodyFrom(modulesPath)
                .select("div#all-modules li.toctree-l1 a")
                .parallelStream()
                .map(a -> a.attr("href"))
                .map(this::readModule)
                .collect(Collectors.toList());
        return new AnsibleModules(modules);
    }

    private static AnsibleVersion tryParseVersion(String text, Pattern versionPattern) {
        Matcher matcher = versionPattern.matcher(text);
        if (matcher.matches()) {
            return AnsibleVersion.valueOf(matcher.group(1));
        }
        return AnsibleVersion.DEFAULT_VERSION;
    }

    public AnsibleModule readModule(String modulePath) {
        try {
            System.err.println("parsing " + modulePath + "...");
            Element doc = getBodyFrom(modulePath);
            AnsibleModule module = new AnsibleModule();
            String title = doc.select("div#page-content div.section h1").get(0).ownText();
            String titleParts[] = title.split(" - ");
            module.setName(titleParts[0]);
            module.setInfo(titleParts[1]);
            module.setSynopsis(doc.select("div#synopsis > p").get(0).text());
            module.setSinceVersion(tryParseVersion(doc.select("div#synopsis .versionmodified").text(), MODULE_VERSION_PATTERN));
            Elements options = doc.select("div#options");
            // might not have any options
            if (!options.isEmpty()) {
                module.addOptions(parseOptions(options.get(0)));
            }
            return module;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read module info from " + modulePath, e);
        }
    }

    private Collection<AnsibleModuleOption> parseOptions(Element optionsDiv) {
        return optionsDiv.select("table tr").stream()
                .map(tr -> tr.select("td"))
                .filter(cells -> !cells.isEmpty())
                .map(cells -> {
                    AnsibleModuleOption option = new AnsibleModuleOption();
                    option.setName(cells.get(0).text());
                    option.setRequired(cells.get(1).text().contains("yes"));
                    option.setDefaultValue(emptyToNull(cells.get(2).text()));
                    option.setChoices(listItemsToString(cells.get(3).child(0)));
                    option.setComments(cells.get(4).text());
                    option.setSinceVersion(tryParseVersion(option.getComments(), OPTION_VERSION_PATTERN));
                    return option;
                })
                .collect(Collectors.toList());
    }

    private String emptyToNull(String s) {
        return s.isEmpty() ? null : s;
    }

    private List<String> listItemsToString(Element unorderedList) {
        return unorderedList.select("li").stream()
                .map(Element::text)
                .collect(Collectors.toList());
    }

}
