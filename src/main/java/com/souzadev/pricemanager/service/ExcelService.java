package com.souzadev.pricemanager.service;

import com.souzadev.pricemanager.models.entities.Produto;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public void exportarProdutosParaExcel(List<Produto> produtos, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Produtos");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle priceStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        priceStyle.setDataFormat(format.getFormat("R$ #,##0.00"));
        priceStyle.setBorderBottom(BorderStyle.THIN);
        priceStyle.setBorderTop(BorderStyle.THIN);
        priceStyle.setBorderLeft(BorderStyle.THIN);
        priceStyle.setBorderRight(BorderStyle.THIN);
        priceStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle zebraStyle = workbook.createCellStyle();
        zebraStyle.cloneStyleFrom(cellStyle);
        zebraStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        zebraStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle zebraPriceStyle = workbook.createCellStyle();
        zebraPriceStyle.cloneStyleFrom(priceStyle);
        zebraPriceStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        zebraPriceStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        String[] colunas = {"ID", "Nome", "Quantidade", "Preço de Venda"};
        Row header = sheet.createRow(0);
        for (int i = 0; i < colunas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(colunas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowCount = 1;
        for (Produto p : produtos) {
            Row row = sheet.createRow(rowCount);
            boolean isZebra = rowCount % 2 == 0;

            Cell idCell = row.createCell(0);
            idCell.setCellValue(p.getId());
            idCell.setCellStyle(isZebra ? zebraStyle : cellStyle);

            Cell nameCell = row.createCell(1);
            nameCell.setCellValue(p.getNome());
            nameCell.setCellStyle(isZebra ? zebraStyle : cellStyle);

            Cell quantityCell = row.createCell(2);
            quantityCell.setCellValue(p.getQuantidade());
            quantityCell.setCellStyle(isZebra ? zebraStyle : cellStyle);

            Cell priceCell = row.createCell(3);
            priceCell.setCellValue(p.getPrecoVenda());
            priceCell.setCellStyle(isZebra ? zebraPriceStyle : priceStyle);

            rowCount++;
        }

        for (int i = 0; i < colunas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        sheet.createFreezePane(0, 1);

        // ===== Resposta HTTP =====
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=produtos.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
