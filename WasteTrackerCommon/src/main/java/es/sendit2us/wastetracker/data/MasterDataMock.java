package es.sendit2us.wastetracker.data;

import java.util.Vector;

public class MasterDataMock {

	private static Vector residualFamilies;
	private static Vector residualCategories;
	private static Vector residuals;
	
	private static Vector containerTypes;
	private static Vector incidentTypes;
	
	static {
		ResidualFamilyType family;
		ResidualCategoryType category;
		ResidualType residual;
		
		residualFamilies = new Vector();
		residualCategories = new Vector();
		residuals = new Vector();
		
		family = createFamily(1, "RAEE", "Residuos de aparatos el�ctricos y electr�nicos");
		residualFamilies.addElement(family);
		
		category = createCategory(family, 1, "1", "Grandes electrodom�sticos");
		residualCategories.addElement(category);
		
		residual = createResidual(category, 1, "1.01.03", "Hogares - Aire acondicionado");
		residuals.addElement(residual);
		residual = createResidual(category, 22, "1.01.02", "Hogares - Frigor�ficos, congeladores y otros equipos refrigeradores");
		residuals.addElement(residual);
		residual = createResidual(category, 25, "1.01.99", "Hogares - Otros grandes electrodom�sticos");
		residuals.addElement(residual);
		
		category = createCategory(family, 2, "2", "Peque�os electrodom�sticos");
		residualCategories.addElement(category);
		
		residual = createResidual(category, 26, "1.02.99", "Hogares - Otros peque�os electrodom�sticos");
		residuals.addElement(residual);
		residual = createResidual(category, 49, "2.02.99", "No hogares - Otros peque�os electrodom�sticos");
		residuals.addElement(residual);

		category = createCategory(family, 3, "3", "Equipos de inform�tica y telecomunicaciones");
		residualCategories.addElement(category);
		
		residual = createResidual(category, 27, "1.03.02", "Hogares - Ordenadores personales y port�tiles");
		residuals.addElement(residual);
		residual = createResidual(category, 28, "1.03.99", "Hogares - Otros equipos de inform�tica y comunicaciones");
		residuals.addElement(residual);
		
		/***/
		
		family = createFamily(2, "PILAS", "Pilas y acumuladores");
		residualFamilies.addElement(family);
		
		category = createCategory(family, 19, "17", "Pilas y acumuladores");
		residualCategories.addElement(category);
		
		residual = createResidual(category, 115, "01.11.5", "Bater�as de automoci�n");
		residuals.addElement(residual);
		residual = createResidual(category, 116, "01.11.4", "Acumuladores (Ni-Cd y resto)");
		residuals.addElement(residual);
		residual = createResidual(category, 117, "01.11.2", "Pilas bot�n");
		residuals.addElement(residual);
		
		/***/
		
		containerTypes = new Vector();
		containerTypes.addElement(createContainer(1, "PEQ", "Pallet 8 cajas peque�as de cart�n"));
		containerTypes.addElement(createContainer(2, "GR", "Pallet caja grande de cart�n"));
		containerTypes.addElement(createContainer(4, "UNISL", "Unidades sueltas"));
	}
	
	public static Vector getResidualFamilies() {
		return residualFamilies;
	}
	
	public static Vector getResidualCategories() {
		return residualCategories;
	}
	
	public static Vector getResiduals() {
		return residuals;
	}
	
	public static Vector getContainerTypes() {
		return containerTypes;
	}
	
	private static ResidualFamilyType createFamily(long id, String code, String desc) {
		ResidualFamilyType obj = new ResidualFamilyType();
		obj.setId(id);
		obj.setCode(code);
		obj.setDescription(desc);
		return obj;
	}
	
	private static ResidualCategoryType createCategory(ResidualFamilyType family, long id, String code, String desc) {
		ResidualCategoryType obj = new ResidualCategoryType();
		obj.setId(id);
		obj.setCode(code);
		obj.setDescription(desc);
		obj.setFamily(family);
		return obj;
	}
	
	private static ResidualType createResidual(ResidualCategoryType category, long id, String code, String desc) {
		ResidualType obj = new ResidualType();
		obj.setId(id);
		obj.setCode(code);
		obj.setDescription(desc);
		obj.setCategory(category);
		return obj;
	}
	
	private static ContainerType createContainer(long id, String code, String desc) {
		ContainerType obj = new ContainerType();
		obj.setId(id);
		obj.setCode(code);
		obj.setDescription(desc);
		return obj;
	}
}
