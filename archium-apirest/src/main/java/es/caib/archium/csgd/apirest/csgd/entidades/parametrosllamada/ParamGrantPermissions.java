package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;


import es.caib.archium.csgd.apirest.constantes.Permisos;

import java.util.List;

public class ParamGrantPermissions {
	private List<String> nodeIds;
	private List<String> authorities;
	private Permisos permission;
	
	public List<String> getNodeIds() {
		return nodeIds;
	}
	public void setNodeIds(List<String> nodeIds) {
		this.nodeIds = nodeIds;
	}
	public List<String> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	public Permisos getPermission() {
		return permission;
	}
	public void setPermission(Permisos permission) {
		this.permission = permission;
	}


	@Override
	public String toString() {
		return "ParamGrantPermissions{" +
				"nodeIds=" + nodeIds +
				", authorities=" + authorities +
				", permission=" + permission +
				'}';
	}
}
