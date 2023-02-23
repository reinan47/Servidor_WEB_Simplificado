import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8001);
		while (true) {
			Socket s = server.accept();
			ThreadServer thServer = new ThreadServer(s);
			thServer.start();
		}
	}
}