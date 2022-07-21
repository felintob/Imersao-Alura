import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.io.InputStream;


public class App {
    public static void main(String[] args) throws Exception {
        
        //fazer uma conexão http e pegar o codigo da api
        String url = "https://api.mocki.io/v2/549a5d8b/Top250Movies";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        
        
        // pegar só os dados que interessam (titulo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        //exibi e manipular os dados 
        for(Map<String,String> filme : listaDeFilmes) {

            String urlImagem = filme.get(key: "image");
            String titulo = filme.get(key: "title");
            InputStream inputStream = new URL(urlImagem).openStream();

            String nomeArquivo = titulo + ".png";

            var geradora = new GeradoraDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo);

            System.out.println(filme.get(key: "\u001b[37m tittle"));
            System.out.println(filme.get(key: "image"));
            System.out.println(filme.get(key: "\uD83C\uDF1F imDbRating"));
            System.out.println();
        }
    }
}
