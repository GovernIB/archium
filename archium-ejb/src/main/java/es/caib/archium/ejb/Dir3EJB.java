package es.caib.archium.ejb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.ejb.objects.Dir3;
import es.caib.archium.ejb.service.Dir3Service;
import es.caib.archium.ejb.utils.CSVReader;

@Stateless
@RolesAllowed({Constants.ACH_GESTOR,Constants.ACH_CSGD})
public class Dir3EJB  implements Dir3Service  {

	@Override
	public List<Dir3> getAll() throws I18NException {
		List<Dir3> list = new ArrayList<Dir3>();
		
		CSVReader reader;
		try {
			reader = new CSVReader("/data/DIR3.csv", ";");
			
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
			
		} catch (FileNotFoundException e) {
			throw new I18NException("dir3.getAll.FileNotFoundException", this.getClass().getSimpleName(), "getAll");
		} catch (IOException e) {
			throw new I18NException("dir3.getAll.IOException", this.getClass().getSimpleName(), "getAll");
		} catch (Exception e) {
			throw new I18NException("dir3.getAll.Exception", this.getClass().getSimpleName(), "getAll");
		}

		
		
	}
		
}
