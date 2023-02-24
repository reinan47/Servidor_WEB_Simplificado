import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class cliente {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			System.out.println("conectando ...");
			TimeUnit.SECONDS.sleep(1);
			Socket clientSocket = new Socket("localhost", 8001);
			System.out.println("Conectado :\")");
			System.out.println("Digite o nome do arquivo HTML sem extensão: ");
			Scanner ler = new Scanner(System.in);
			String arq = ler.nextLine();
			ler.close();
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeBytes(arq + ".html" + '\n');
			clientSocket.setSoTimeout(60000);

			// Criando arquivo que sera recebido pelo servidor
			FileOutputStream fileOut = new FileOutputStream("arquivoRecebibo.txt");

			// Criando canal de transferencia
			InputStream socketIn = clientSocket.getInputStream();

			// Lendo o arquivo recebido pelo socket e gravando no
			// arquivo local
			System.out.println("Recebendo Arquivo: " + arq + ".html");

			byte[] cbuffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = socketIn.read(cbuffer)) != -1) {

				fileOut.write(cbuffer, 0, bytesRead);
			}
			fileOut.close();

			clientSocket.close();
			System.out.println("Arquivo Recebido: Arquivo: " + arq + ".html");
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
