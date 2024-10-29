package school.sptech;

import com.mysql.cj.log.Log;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class S3Provider {

    private final AwsSessionCredentials credentials;

    public S3Provider() {
        this.credentials = AwsSessionCredentials.create(
                "ASIAT5KJDZCZO2MFT5EP",
                "YMXe3v2f8yKV4H8qo+HmLW8jgvp4nEwwtJGIY+k/",
                "IQoJb3JpZ2luX2VjENz//////////wEaCXVzLXdlc3QtMiJIMEYCIQCm9EYBpaogQye3/T75kQFTrx96GiVoVcWKsGWw6kJIIwIhAKxuRaFeSZhhlQTLhNGYBGhk5/G1sO3yYyY+e67yiFL2KrUCCFUQABoMMjY5MTI1NjY2OTk0IgxWnID4K5L1b/BygtMqkgJW5bW3WBeca8KqinoX/2S6f7LE6mJUA/o0LIoR9CS6CrLTM/ucNFNxwMqXu1ikmkRRCWVA2jJ4b2mbudlGA7snOT0jUTgmsycbZ66DfFV6Wj4ICjzsnJBxrDjl7iGq5Z9U8wicdqkUfn8T0sfYUf365RQG4xGMZdFX48j0LqSk+p/2M/fehLL0rPJNMdEXc0NqXsUxNXPOL/U99g13oCHpsFRYbTLGF+czkEdoJgmF38598gPbJ+IDTAtZJxUa7my1A45FmsPXgBLE1XXYbMc3NXQ73nB8pN+vobtat8Rie8I13M2VbZFhDjFB2nCcIXh0nruAfxtf1Isx5lZ5cUikfIc0pl6c+YHxdN8WED3kLMBjMJO1gbkGOpwBltwrZQ+JXdSL7mjiYHGlFr16WZ6tDx6oimHVGT3B+cslxEFpFkLnCFsszlrPuh3dHygNfDWyavOmMao5djKU/T/eFLBkcvRDnJ3FkQ51DfTtY8F6Aob7IUHwJXn258XWzuR6IQVA5hPMjHgt/ceVoMAL0zNNuwxyT8as0ofYLgCaT1wqx5nIZqs+RnS2Mv4iR7pjeyjKzJpBUMlP"
        );
    }

    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(() -> credentials)
                .build();
    }

    public void getS3file() {
        S3Client s3Client = new S3Provider().getS3Client();
        String bucketName = "infinity-bucket";
        LogSistema log = new LogSistema();

        String nomeArquivoExcel = "";

        try {
            ListObjectsRequest listObjects = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build();

            List<S3Object> objects = s3Client.listObjects(listObjects).contents();


            for (S3Object object : objects) {
                Boolean objetoExcel = object.key().endsWith(".xlsx") || object.key().endsWith(".xls");

                if (objetoExcel) {
                    nomeArquivoExcel = object.key();
                }
            }
        } catch (S3Exception e) {
            log.mandarMensagemParaLog("Erro ao listar objetos no bucket: " + e.getMessage());
            System.err.println("Erro ao listar objetos no bucket: " + e.getMessage());
        }

        try {
            GetObjectRequest getS3file = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(nomeArquivoExcel)
                    .build();

            InputStream arquivo = getS3Client().getObject(getS3file);

            LeitorArquivo leitorArquivo = new LeitorArquivo();
            QuerysBD querys = new QuerysBD();
            Boolean arquivoJaLido = querys.alterarStatusArquivo(nomeArquivoExcel);

            if (arquivoJaLido) {

                log.mandarMensagemParaLog("Todos os arquivos ja foram lidos");

            } else {
                log.mandarMensagemParaLog("Arquivo novo, inicializando extração");
                leitorArquivo.extrairRegistros(getS3file.key(), arquivo);
                log.mandarMensagemParaLog("Extração finalizada");

            }

        } catch (S3Exception e) {
            log.mandarMensagemParaLog("Erro ao obter arquivo do bucket: " + e.getMessage());
            System.out.println("Erro ao obter arquivo do bucket: " + e.getMessage());
        }
    }
}

