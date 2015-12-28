package blackcrystal.model;

public class Resource {

    public String name;

    public ResourceType type;

    public String directoryPath;

    public boolean acceptHostkey;

    public String remote;

    public String version;

    public String repository;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (acceptHostkey != resource.acceptHostkey) return false;
        if (name != null ? !name.equals(resource.name) : resource.name != null) return false;
        if (type != resource.type) return false;
        if (directoryPath != null ? !directoryPath.equals(resource.directoryPath) : resource.directoryPath != null)
            return false;
        if (remote != null ? !remote.equals(resource.remote) : resource.remote != null) return false;
        if (version != null ? !version.equals(resource.version) : resource.version != null) return false;
        return !(repository != null ? !repository.equals(resource.repository) : resource.repository != null);

    }


}


