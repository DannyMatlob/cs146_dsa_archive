import java.util.ArrayList;
import java.util.HashSet;

public class Schedule {
	class Job {
		//Key
		int time = 0;
		//Outgoing adjacency list
		ArrayList<Job> outgoing = new ArrayList<Job>();
		//Topological Sort inDegree
		int inDegree = 0;
		int tempInDegree = 0;
		//d = time until this job can start
		int d = 0;
		
		private Job(int time) {
			this.time = time;
		}
		
		public void relax() {
			for (Job j : outgoing) {
				if (j.d < time + d) {
					j.d = time+d;
				}
			}
		}
		
		public void requires(Job j) {
			topologicallySorted = false;
			j.outgoing.add(this);
			inDegree++;
		}
		
		public int start() {
			finish();	
			if (tempInDegree>0) return -1;
			return d;
		}
		
		
	}
	//End of Job Class
	//------------------
	boolean topologicallySorted = false;
	ArrayList<Job> Jobs = new ArrayList<Job>();
	ArrayList<Job> topologicalOrder = new ArrayList<Job>();
	
	public Schedule() {}
	
	//Helper Methods
	public void updateInDegree() {
		for (Job j : Jobs) {
			j.tempInDegree = j.inDegree;
			if (j.tempInDegree==0) topologicalOrder.add(j);
		}
	}
	
	public void topsort() {
		if (topologicallySorted) return;
		topologicalOrder.clear();
		updateInDegree();
		for (int k = 0; k<topologicalOrder.size(); k++) {
			Job j = topologicalOrder.get(k);
			for (Job i : j.outgoing) {
				i.tempInDegree--;
				if (i.tempInDegree==0) {
					//System.out.println("TopOrder updated, i = " + i.time + ", j = " + j.time);
					topologicalOrder.add(i);
				}	
			}
		}
		topologicallySorted = true;
	}
	
	//End of Helper Methods
	
	public Job insert(int time) {
		Job j = new Job(time);
		Jobs.add(j);
		topologicalOrder.add(j);
		return j;
	}
	
	public Job get(int index) {
		return Jobs.get(index);
	}
	
	int maxTime = 0;
	public int finish() {
		if (topologicallySorted) return maxTime;
		topsort();
		if (Jobs.size() > topologicalOrder.size()) {
			maxTime = -1;
			return maxTime;
		}
		for (Job j : topologicalOrder) {
			j.relax();
			maxTime = (j.d + j.time > maxTime) ? j.d+j.time : maxTime;
		}
		return maxTime;
	}
}
