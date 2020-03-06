/**
 * 
 */
package com.fibeso.captacion.pfuneraria.repository;

/**
 * @author Javier Pérez Sánchez
 *
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

@Transactional
@Repository
public class ImprimeConveniosRepository {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ResourceLoader resourceLoader2;

	public JasperPrint exportPdfFile(String convenio) throws SQLException, JRException, IOException {
		Connection conn = jdbcTemplate.getDataSource().getConnection();

		conn.setCatalog("fibesocap");
		conn.setSchema("esqfibeso");
		
		
		String path = resourceLoader.getResource("classpath:Convenio.jrxml").getURI().getPath();

		JasperReport jasperReport = JasperCompileManager.compileReport(path);

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();
		//TODO	objeto de texto de convenio
		
		//parameters.put("FolioConvenio","DOC-CREM-20190104-00001-00001");
		//Locale locale = new Locale("es","MX");
		
		parameters.put("FolioConvenio",convenio);
		//parameters.put(JRParameter.REPORT_LOCALE, locale);

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
		if(!conn.isClosed()) {
			conn.close();
		}
		return print;
	}
	
	public JasperPrint exportPdfFile(String rfcconvenio, Integer rfc) throws SQLException, JRException, IOException {
		JasperPrint print = null;
		try {
		Connection conn = jdbcTemplate.getDataSource().getConnection();

		conn.setCatalog("fibesocap");
		conn.setSchema("esqfibeso");
		
		String path = resourceLoader2.getResource("classpath:Adenda.jrxml").getURI().getPath();
		System.out.println("ruta de la adenda: " + path);
		JasperReport jasperReport = JasperCompileManager.compileReport(path);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("rfc",rfcconvenio);
		//JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
		print = JasperFillManager.fillReport(jasperReport, parameters, conn);
		if(!conn.isClosed()) {
			conn.close();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return print;
	}
	
}
