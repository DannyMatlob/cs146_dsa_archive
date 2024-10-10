import java.util.ArrayList;
import java.util.HashSet;

public class ScheduleTopologicalInsert {
	class Job {
		//Key
		int time = 0;
		//Outgoing adjacency list
		ArrayList<Job> outgoing = new ArrayList<Job>();
		//Topological Sort inDegree
		int inDegree = 0;
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
			timesCalculated = false;
			j.outgoing.add(this);
			
		}
		
		public int start() {
			if (!timesCalculated) finish();	
			if (inDegree>0) return -1;
			return d;
		}
		
		
	}
	//End of Job Class
	//------------------
	boolean timesCalculated = false;
	ArrayList<Job> Jobs = new ArrayList<Job>();
	ArrayList<Job> topologicalOrder = new ArrayList<Job>();
	
	public ScheduleTopologicalInsert() {}
	
	//Helper Methods
	public void calculateInDegree() {
		for (Job j : Jobs) {
			j.inDegree = 0;
		}
		
		for (Job j : Jobs) {
			for (Job i : j.outgoing) {
				i.inDegree++;
			}
		}
	}
	
	//End of Helper Methods
	public Job insert(int time) {
		timesCalculated = false;
		Job j = new Job(time);
		Jobs.add(j);
		topologicalOrder.add(j);
		return j;
	}
	
	public Job get(int index) {
		return Jobs.get(index);
	}
	
	public int finish() {
		if (Jobs.size() > topologicalOrder.size()) {
			return -1;
		}
		for (Job j : topologicalOrder) {
			j.relax();
		}
		int maxTime = 0;
		for (Job j : Jobs) {
			maxTime = (j.d + j.time > maxTime) ? j.d+j.time : maxTime;
		}
		return maxTime;
	}
}
