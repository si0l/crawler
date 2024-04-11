package dev.crawler.spirits.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import dev.crawler.spirits.dto.ProductDto;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean isExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<ProductDto> excelToProducts(InputStream is, String pageName) throws IOException {
        Workbook workbook = new XSSFWorkbook(is);

        Sheet sheet = workbook.getSheet(pageName);
        Iterator<Row> rows = sheet.iterator();

        List<ProductDto> products = new ArrayList<ProductDto>();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }

            Iterator<Cell> cellsInRow = currentRow.iterator();

            ProductDto product = new ProductDto();

            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();

                switch (cellIdx) {
                    case 0 -> product.setSearch(currentCell.getStringCellValue());
                    case 1 -> product.setInitialText(currentCell.getStringCellValue());
                    case 2 -> product.setBrand(currentCell.getStringCellValue());
                }

                cellIdx++;
            }

            products.add(product);
        }

        workbook.close();

        return products;
    }
}