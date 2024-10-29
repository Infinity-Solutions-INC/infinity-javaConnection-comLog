package school.sptech;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LeitorArquivo {
    public void extrairRegistros(String nomeArquivo, InputStream arquivo) {

        IOUtils.setByteArrayMaxOverride(10000 * 1024 * 1024);
        LogSistema log = new LogSistema();
        try {
            log.mandarMensagemParaLog("Iniciando leitura do arquivo: %s".formatted(nomeArquivo));
            System.out.println("\nIniciando leitura do arquivo %s\n".formatted(nomeArquivo));

            Workbook workbook;

            if (nomeArquivo.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(arquivo);
            } else {
                workbook = new HSSFWorkbook(arquivo);
            }

            Sheet sheet = workbook.getSheetAt(0);

            List<Registro> dadosCapturados = new ArrayList<>();

            for (Row row : sheet) {

                if (row.getRowNum() >= 10) {
                    Cell celulaNomeCurso  = row.getCell(12);
                    Cell celulaNomeAreaCurso = row.getCell(14);
                    Cell celulaAnoReferencia = row.getCell(16);
                    Cell celulaQtdIngressantes = row.getCell(21);
                    Cell celulaQtdAlunosPermanencia = row.getCell(22);

                    Boolean todasCelulasNotNull = celulaNomeCurso != null &&
                            celulaNomeAreaCurso != null && celulaAnoReferencia != null &&
                            celulaQtdIngressantes != null && celulaQtdAlunosPermanencia != null;

                    if (todasCelulasNotNull) {
                        String nomeCurso = celulaNomeCurso.getStringCellValue().trim();
                        String nomeAreaCurso = celulaNomeAreaCurso.getStringCellValue().trim();
                        Integer anoReferencia = (int) celulaAnoReferencia.getNumericCellValue();
                        Integer qtdIngressantes = (int) celulaQtdIngressantes.getNumericCellValue();
                        Integer qtdAlunosPermanencia = (int) celulaQtdAlunosPermanencia.getNumericCellValue();

                        Boolean areasQueVamosUsar = nomeAreaCurso.equals("Saúde e bem-estar") ||
                                nomeAreaCurso.equals("Computação e Tecnologias da Informação e Comunicação (TIC)") ||
                                nomeAreaCurso.equals("Artes e humanidades");

                        if (areasQueVamosUsar) {
                            Registro registro = new Registro(nomeCurso, nomeAreaCurso, anoReferencia,
                                    qtdIngressantes, qtdAlunosPermanencia);
                            dadosCapturados.add(registro);
                        }
                    }
                }

            }

            workbook.close();
            log.mandarMensagemParaLog("leitura finalizada");
            System.out.println("LEITURA FINALIZADA");
            QuerysBD query = new QuerysBD();
            log.mandarMensagemParaLog("Inicializando inserção no banco");
            query.inserirDados(dadosCapturados);
            log.mandarMensagemParaLog("inserção no banco finalizada");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
