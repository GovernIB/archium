package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.objects.Dir3;
import es.caib.archium.ejb.service.Dir3Service;
import es.caib.archium.ejb.utils.CSVReader;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class Dir3EJB  implements Dir3Service  {

	@Override
	public List<Dir3> getAll() {
		List<Dir3> list = new ArrayList<Dir3>();
		
		CSVReader reader = new CSVReader("/data/DIR3.csv", ";");
		
		for(String[] linea: reader.getData()) {
			boolean datoEncontrado = false;
			int i = 0;
			while(i<linea.length && datoEncontrado==false) {
				if(!linea[i].isBlank()) {
					String[] partes = linea[i].split("-");
					Dir3 object = new Dir3(partes[0], partes[1]);
					list.add(object);
					datoEncontrado = true;
				}
				i++;
			}
			
			
		}
		
		return list;
		
	}
		
}
