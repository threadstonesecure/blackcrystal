package blackcrystal.model;

public class Resource {

    public String name;

    public String type;

    public String version;

    public String repository;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (name != null ? !name.equals(resource.name) : resource.name != null) return false;
        if (type != null ? !type.equals(resource.type) : resource.type != null) return false;
        if (version != null ? !version.equals(resource.version) : resource.version != null) return false;
        return !(repository != null ? !repository.equals(resource.repository) : resource.repository != null);

    }

}


