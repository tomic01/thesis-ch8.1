package instAwarePlanning.tests.comm;

import instAwarePlanning.SAM.SamSimple;
import instAwarePlanning.communication.Comm;

public class TestCommSAM {

	public static void main(String[] args) {
		Comm comm = new Comm();
		
		SamSimple sam = new SamSimple(comm);

	}

}
