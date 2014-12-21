package elixir.model;

import java.util.ArrayList;
import java.util.List;

public class Articles {

	public static List<Signature> sign(List<Article> articles) {
		List<Signature> signatures = new ArrayList<Signature>();
		
		for (Article article : articles) {
			try {
				signatures.add(article.sign());
				
			} catch (SignatureFailureException e) {
				// do - nothing
			}
		}
		
		return signatures;
	}

}
