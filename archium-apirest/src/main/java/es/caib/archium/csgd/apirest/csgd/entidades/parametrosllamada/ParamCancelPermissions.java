package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;


import es.caib.archium.csgd.apirest.constantes.Permisos;

import java.util.List;

public class ParamCancelPermissions {
	private List<String> nodeIds;
	private List<String> authorities;

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

	@Override
	public String toString() {
		return "ParamCancelPermissions{" +
				"nodeIds=" + nodeIds +
				", authorities=" + authorities +
				'}';
	}
}