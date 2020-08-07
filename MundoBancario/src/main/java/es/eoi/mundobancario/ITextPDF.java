package es.eoi.mundobancario;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.service.ReportsService;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

public class ITextPDF {


	public static void imprimirPDF_EOI_BANK_CLIENTE_000(ClienteDto clientedto) throws FileNotFoundException, DocumentException{
		
		crearPDF_EOI_BANK_CLIENTE_000(clientedto);
		
	}
	
	public static void crearPDF_EOI_BANK_CLIENTE_000(ClienteDto clientedto) throws FileNotFoundException, DocumentException {
		
		// Se crea el documento
        Document documento = new Document();
        
        // El OutPutStream para el fichero donde crearemos el PDF
        FileOutputStream ficheroPDF = new FileOutputStream("EOI_BANK_CLIENTE_00"+clientedto.getId()+".pdf");
        
        // Se asocia el documento de OutPutStream
        PdfWriter.getInstance(documento, ficheroPDF);
        
        // Se abre el documento
        documento.open();
		
     // Parrafo
        Paragraph titulo = new Paragraph("Clientes con sus prestamos y amortizaciones realizados \n\n",
                FontFactory.getFont("arial",
                        22,
                        Font.BOLD,
                        BaseColor.DARK_GRAY
                        )
        );
        
        // Añadimos el titulo al documento
        documento.add(titulo);
        
     // Creamos una tabla
        PdfPTable tabla = new PdfPTable(3);
        tabla.addCell("CLIENTE");
        tabla.addCell("CUENTA");
        tabla.addCell("amortizaciones");
        
        
        
        PdfPTable cliente = new PdfPTable(1);
        cliente.getDefaultCell().setBorder(0);
        cliente.addCell("Id:"+clientedto.getId());
        cliente.addCell("Usuario:"+clientedto.getUsuario());
        cliente.addCell("Nombre:"+clientedto.getNombre());
        cliente.addCell("Email:"+clientedto.getEmail());
        
        
        PdfPCell celdacliente = new PdfPCell(cliente);
        celdacliente.setBorderWidthBottom(0);
        tabla.addCell(celdacliente);
        for (CuentaDto cuentadto : clientedto.getCuentas()){
        	
        	
        	PdfPTable prestamos = new PdfPTable(1);
        	prestamos.getDefaultCell().setBorder(0);
            PdfPTable amortizaciones = new PdfPTable(1);
            
        	 PdfPTable cuenta = new PdfPTable(1);
        	 cuenta.getDefaultCell().setBorder(0);
        	 
        	cuenta.addCell("Num_cuenta:" +cuentadto.getNum_cuenta());
        	cuenta.addCell("Alias:" +cuentadto.getAlias());
        	cuenta.addCell("Saldo:" +cuentadto.getSaldo());
        	
        	prestamos.addCell(cuenta);
        	
        	for (MovimientoDto movimientodto : cuentadto.getMovimientos()){
             	 
        		PdfPTable movimiento = new PdfPTable(1);
           	 	movimiento.getDefaultCell().setBorder(0);
        		movimiento.addCell("Descripcion:"+movimientodto.getDescripcion());
        		movimiento.addCell("Fecha:"+movimientodto.getDescripcion());
        		
        		if(movimientodto.getTipomovimiento().getId()==1) {
        			PdfPCell importe = new PdfPCell(new Phrase("Importe:" +movimientodto.getImporte(),
        	                FontFactory.getFont("arial",
        	                        12,
        	                        BaseColor.GREEN
        	                        )));
        			importe.setBorder(0);
        			movimiento.addCell(importe);
        		}else if(movimientodto.getTipomovimiento().getId()==2){
        			PdfPCell importe = new PdfPCell(new Phrase("Importe:" +movimientodto.getImporte(),
        	                FontFactory.getFont("arial",
        	                		12,
        	                        BaseColor.GREEN
        	                        )));
        			importe.setBorder(0);
        			movimiento.addCell(importe);
        		}else {
        			PdfPCell importe = new PdfPCell(new Phrase("Importe:" +movimientodto.getImporte(),
        	                FontFactory.getFont("arial",
        	                		12,
        	                        BaseColor.RED
        	                        )));
        			importe.setBorder(0);
            		movimiento.addCell(importe);
        		}
        		movimiento.addCell("Tipo:"+movimientodto.getTipomovimiento().getTipo());
        		        		
        		amortizaciones.addCell(movimiento);
    		}
        	
        	tabla.addCell(prestamos);
            tabla.addCell(amortizaciones);
            PdfPCell celdavacia = new PdfPCell(new Phrase(""));
            celdavacia.setBorderWidthTop(0);
            tabla.addCell(celdavacia);
		}
        	
        
        
        
        
        
     // Añadimos la tabla al documento
        documento.add(tabla);
        
        // Se cierra el documento
        documento.close();
	}
	
	
public static void imprimirPDF_EOI_BANK_PRESTAMO_000(ClienteDto clientedto) throws FileNotFoundException, DocumentException{
		
		crearPDF_EOI_BANK_PRESTAMO_000(clientedto);
		
	}



public static void crearPDF_EOI_BANK_PRESTAMO_000(ClienteDto clientedto) throws FileNotFoundException, DocumentException {
	
	// Se crea el documento
    Document documento = new Document();
    
    // El OutPutStream para el fichero donde crearemos el PDF
for (CuentaDto cuentadto : clientedto.getCuentas()){
    	
    	for (PrestamoDto prestamodto : cuentadto.getPrestamos()) {
    FileOutputStream ficheroPDF = new FileOutputStream("EOI_BANK_PRESTAMO_000"+prestamodto.getId()+".pdf");
    // Se asocia el documento de OutPutStream
    PdfWriter.getInstance(documento, ficheroPDF);
    	}}
   
    
    // Se abre el documento
    documento.open();
	
 // Parrafo
    Paragraph titulo = new Paragraph("Clientes prestamos y amortizaciones \n\n",
            FontFactory.getFont("arial",
                    22,
                    Font.BOLD,
                    BaseColor.DARK_GRAY
                    )
    );
    
    // Añadimos el titulo al documento
    documento.add(titulo);
    
 // Creamos una tabla
    PdfPTable tabla = new PdfPTable(3);
    tabla.addCell("CLIENTE");
    tabla.addCell("PRESTAMO");
    tabla.addCell("AMORTIZACIONES");
    
    
    
    PdfPTable cliente = new PdfPTable(1);
    cliente.getDefaultCell().setBorder(0);
    cliente.addCell("Id:"+clientedto.getId());
    cliente.addCell("Usuario:"+clientedto.getUsuario());
    cliente.addCell("Nombre:"+clientedto.getNombre());
    cliente.addCell("Email:"+clientedto.getEmail());
    
    
    
    PdfPCell celdacliente = new PdfPCell(cliente);
    tabla.addCell(celdacliente);
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    for (CuentaDto cuentadto : clientedto.getCuentas()){
    	
    	for (PrestamoDto prestamodto : cuentadto.getPrestamos()) {
    		
    	
    		PdfPTable prestamos = new PdfPTable(1);
    		prestamos.getDefaultCell().setBorder(0);
    		PdfPTable amortizaciones = new PdfPTable(1);
        
    		PdfPTable prestamo = new PdfPTable(1);
    		prestamo.getDefaultCell().setBorder(0);
    	 
    		prestamo.addCell("Id:" +prestamodto.getId());
    		prestamo.addCell("Descripcion:" +prestamodto.getDescripcion());
    		prestamo.addCell("Fecha:" +formatter.format(prestamodto.getFecha()));
    		prestamo.addCell("Importe:" +prestamodto.getImporte());
    		prestamo.addCell("Plazos:" +prestamodto.getPlazos());
    		prestamo.addCell("Num_cuenta:" +cuentadto.getNum_cuenta());
    	
    		prestamos.addCell(prestamo);
    	
    		for (AmortizacionDto amortizaciondto : prestamodto.getAmortizaciones()){
         	 
    			PdfPTable amortizacion = new PdfPTable(1);
    			amortizacion.getDefaultCell().setBorder(0);
    			amortizacion.addCell("Id:"+amortizaciondto.getId());
    			amortizacion.addCell("Fecha:"+formatter.format(amortizaciondto.getFecha()));
    			amortizacion.addCell("Importe:"+amortizaciondto.getImporte());
    		        		
       	 		amortizaciones.addCell(amortizacion);
    		}
    	
    		tabla.addCell(prestamos);
    		tabla.addCell(amortizaciones);
    		PdfPCell celdavacia = new PdfPCell(new Phrase(""));
    		celdavacia.setBorderWidthTop(0);
    		tabla.addCell(celdavacia);
    	}
	}
    	
    
    
    
    
    
 // Añadimos la tabla al documento
    documento.add(tabla);
    
    // Se cierra el documento
    documento.close();
	
	
}
	
}
