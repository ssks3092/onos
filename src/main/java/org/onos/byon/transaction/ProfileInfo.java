package org.onos.byon.transaction;

import org.onosproject.net.Link;
import org.onosproject.net.Path;

import java.util.List;

public class ProfileInfo {

    private List<Link> links;

    private Path path;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
