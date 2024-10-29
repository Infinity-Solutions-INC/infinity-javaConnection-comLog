package school.sptech;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogSistema {
    private String nomeArquivo = "arquivo.log";
    public void mandarMensagemParaLog (String mensagem){
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss:: ");
        String dataFormatada = data.format(FORMATO_DIA);
        System.out.println(dataFormatada + mensagem);
        gravarNoArquivo(dataFormatada + mensagem);
    }
    private void gravarNoArquivo(String mensagem) {
        // Usando try-with-resources para garantir o fechamento do BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            writer.write(mensagem); // Escrever a mensagem no arquivo
            writer.newLine();       // Adicionar uma nova linha após a mensagem
        } catch (IOException e) {
            System.err.println("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }
}
