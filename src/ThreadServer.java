import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ThreadServer extends Thread {
	Socket s;

	public ThreadServer(Socket s) {
		this.s = s;
	}

	public void run() {
		try {

			EnviarArq(s);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void EnviarArq(Socket s) throws IOException, FileNotFoundException {
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
		// aguardando recebimento do nome do arquivo - timeout 10 segundos
		String arquivo = inFromClient.readLine();
		// verifica se o arquivo existe
		// se existir envia o arquivo
		FileInputStream fileIn = new FileInputStream("./files/" + arquivo);
		OutputStream socketOut = s.getOutputStream();
		// Criando tamanho de leitura
		byte[] cbuffer = new byte[1024];
		int bytesRead;

		// Lendo arquivo criado e enviado para o canal de transferencia
		System.out.println("Enviando Arquivo: " + arquivo);
		while ((bytesRead = fileIn.read(cbuffer)) != -1) {
			socketOut.write(cbuffer, 0, bytesRead);
			socketOut.flush();
		}
		fileIn.close();
		s.close();
		System.out.println("Arquivo Enviado!");
	}
}
