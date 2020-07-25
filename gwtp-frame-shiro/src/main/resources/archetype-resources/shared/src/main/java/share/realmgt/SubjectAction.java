package ${package}.share.realmgt;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import ${package}.share.GetResults;

public class SubjectAction extends UnsecuredActionImpl<GetResults<AccountEntity>> {

	public static enum OP implements IsSerializable { CREATE, READ, UPDATE, DELETE, ENABLE, DISABLE }; 	

	protected SubjectAction(){}

	
	public SubjectAction(Set<AccountEntity> subjects, String password, OP op, int offset, int size, String like, long sequence) {
		this.subjects = subjects;
		this.password = password;
		this.op = op;
		this.offset = offset;
		this.size = size;
		this.like = like;
		this.sequence = sequence;
	}
	
	public long sequence;
	public int offset;
	public int size;
	public String like;
	public OP op;
	public Set<AccountEntity> subjects;
	public String password;
}
