package io.maxilog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleRepresentation {
    protected String id;
    protected String name;
    protected String description;
    /** @deprecated */
    @Deprecated
    protected Boolean scopeParamRequired;
    protected boolean composite;
    protected RoleRepresentation.Composites composites;
    private Boolean clientRole;
    private String containerId;
    protected Map<String, List<String>> attributes;

    public RoleRepresentation() {
    }

    public RoleRepresentation(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RoleRepresentation(String name, String description, boolean scopeParamRequired) {
        this.name = name;
        this.description = description;
        this.scopeParamRequired = scopeParamRequired;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** @deprecated */
    @Deprecated
    public Boolean isScopeParamRequired() {
        return this.scopeParamRequired;
    }

    public RoleRepresentation.Composites getComposites() {
        return this.composites;
    }

    public void setComposites(RoleRepresentation.Composites composites) {
        this.composites = composites;
    }

    public String toString() {
        return this.name;
    }

    public boolean isComposite() {
        return this.composite;
    }

    public void setComposite(boolean composite) {
        this.composite = composite;
    }

    public Boolean getClientRole() {
        return this.clientRole;
    }

    public void setClientRole(Boolean clientRole) {
        this.clientRole = clientRole;
    }

    public String getContainerId() {
        return this.containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Map<String, List<String>> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, List<String>> attributes) {
        this.attributes = attributes;
    }

    public RoleRepresentation singleAttribute(String name, String value) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }

        this.attributes.put(name, Arrays.asList(value));
        return this;
    }

    public static class Composites {
        protected Set<String> realm;
        protected Map<String, List<String>> client;
        /** @deprecated */
        @Deprecated
        protected Map<String, List<String>> application;

        public Composites() {
        }

        public Set<String> getRealm() {
            return this.realm;
        }

        public void setRealm(Set<String> realm) {
            this.realm = realm;
        }

        public Map<String, List<String>> getClient() {
            return this.client;
        }

        public void setClient(Map<String, List<String>> client) {
            this.client = client;
        }

        /** @deprecated */
        @Deprecated
        public Map<String, List<String>> getApplication() {
            return this.application;
        }
    }
}
