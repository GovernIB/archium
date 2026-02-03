package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.objects.Dir3;
import es.caib.archium.ejb.service.Dir3Service;
import es.caib.archium.ejb.utils.CSVReader;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class Dir3EJB  implements Dir3Service  {

	// TODO Integració amb DIR3CAIB

	@Override
	public List<Dir3> getAll() throws I18NException {
		List<Dir3> list = new ArrayList<Dir3>();
		
		CSVReader reader;
		try {
			reader = new CSVReader("/data/DIR3.csv", ";");
			
			for(String[] linea: reader.getData()) {
				boolean datoEncontrado = false;
				int i = 0;
				while(i<linea.length && !datoEncontrado) {
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


	@Override
	public Dir3 findByCodi(String codi) throws I18NException {
		Dir3 dir3 = null;

		CSVReader reader;
		try {
			reader = new CSVReader("/data/DIR3.csv", ";");

			List<String[]> dirs3 = reader.getData();
			boolean dir3Trobat = false;
			int j = 0;
			while (j < dirs3.size() && !dir3Trobat) {
				for (String[] dir : dirs3) {

					boolean datoEncontrado = false;
					int i = 0;
					while (i < dir.length && datoEncontrado == false) {
						if (!dir[i].isBlank()) {
							String[] partes = dir[i].split("-");
							Dir3 object = new Dir3(partes[0], partes[1]);
							if (object.getCodi().equals(codi)) {
								dir3Trobat = true;
								dir3 = object;
							}
							datoEncontrado = true;
						}
						i++;
					}
				}
				j++;
			}
			return dir3;

		} catch (FileNotFoundException e) {
			throw new I18NException("dir3.getAll.FileNotFoundException", this.getClass().getSimpleName(), "findByCodi");
		} catch (IOException e) {
			throw new I18NException("dir3.getAll.IOException", this.getClass().getSimpleName(), "findByCodi");
		} catch (Exception e) {
			throw new I18NException("dir3.getAll.Exception", this.getClass().getSimpleName(), "findByCodi");
		}
	}
}



