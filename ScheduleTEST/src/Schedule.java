import java.util.*;

public class Schedule
{
	ArrayList<Job> jobs;
	int orderIndex;	
	int minCompletionTime;
	boolean needToSort;

	public Schedule() 
	{
		jobs = new ArrayList<Job>();
		needToSort = true;
	}
	
	public int finish()
	{
		topologicalSort();
		
		return minCompletionTime;
	}
		
	//topological sort using Khan's
	private void topologicalSort()
	{
		//if we need to sort, enter if statement
		if(needToSort)
		{
			ArrayList<Job> orderedJobs = new ArrayList<Job>();
			// Add all Jobs with no required jobs/prerequisites to orderedJobs list
			for(Job j: jobs)
			{
				j.tempDegree = j.inDegree;
				j.startTime = 0;	//initialize to 0
				
				if(j.tempDegree == 0)   //checks if it's a prerequisite
				{
					orderedJobs.add(j);
				}
			}
			
			//go through orderedJobs in order
			orderIndex = 0;
			for(orderIndex = 0; orderIndex < orderedJobs.size(); orderIndex++)
			{
				Job a = orderedJobs.get(orderIndex);
				
				//b is adjacent to a / a is a requirement for b
				for(Job b: a.requirements)
				{
					//once found, decrement inDegree of that job
					//if tempDegree is 0 post-decrement, add to list of OrderedJobs
					if((b.tempDegree-=1) == 0){orderedJobs.add(b);}
					
					relax(a, b);
				}
				
				//if the time for job a is greater than the minCompletionTime, set minCompletionTime to aTime
				int aTime = a.jobTime + a.startTime; 
				if(aTime > minCompletionTime)
					minCompletionTime = aTime;
				
			}
						
			//Cycle is found, make minCompletionTime -1 so finish() returns -1
			if(orderedJobs.size() != jobs.size()) {minCompletionTime = -1;}
			//jobs now sorted, so needToSort is now false
			needToSort = false;
		}
	}
	
	
	private void relax(Job u, Job v)
	{
		int d = u.jobTime + u.startTime;
		if(d > v.startTime) {v.startTime = d;}
	}
			
	public class Job
	{
		int jobTime, inDegree, tempDegree, startTime;
		ArrayList <Job> requirements;
		
		private Job(int time) 
		{
			jobTime = time;
			requirements = new ArrayList<Job>();
		}
		
		public void requires(Job j)
		{
			j.requirements.add(this);	
			inDegree++;
			needToSort = true; 
		}
		
		public int start()
		{
			topologicalSort();
			
			//tempDegree should = 0 unless there's a cycle
			//will be accounted for in topological sort
			if(tempDegree > 0){startTime = -1;}
			
			return startTime;
		}
	}
	
	public Job insert(int time)
	{
		Job j = new Job(time);
		jobs.add(j);
		needToSort = true;
		
		return j;
	}
	
	public Job get(int index)
	{
		return jobs.get(index);
	}
}