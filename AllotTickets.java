import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class AllotTickets{

	public void allocateMovieTickets(Integer[][] layout,ArrayList<String> requestList) {

		int cellMax = 0;
		int theatreCapacity = 0;
		int currentCapacity = 0;

		for (int i=0; i<layout.length;i++){
			for (int j=0; j<layout[i].length;j++){

				if(layout[i][j]==0){
					continue;
				} 
				cellMax = Math.max(cellMax,layout[i][j]);
				theatreCapacity += layout[i][j];
			}
		} 

		currentCapacity = theatreCapacity;

		// Resultant HashMap.

		HashMap<String, String> result = new HashMap<String, String>();

		// Temp Hold HashMap.

		HashMap<Integer, List<String>> tempHold = new HashMap<>();



		// Create a HashMap of Requests.

		HashMap<Integer, List<String>> req = new HashMap<>();

		for(String str : requestList){

			String[] splitted = str.split("\\s+");
			String name = splitted[0];
			int requestNum = Integer.parseInt(splitted[1]);

			if(requestNum > theatreCapacity) {
				result.put(name,"Sorry we cant handle ur party");
				continue;
			}

			if(requestNum > cellMax){
				List<String> tmp = new LinkedList<String>();
				if(req.containsKey(requestNum)){
					tmp = tempHold.get(requestNum);
				}
				tmp.add(name);
				tempHold.put(requestNum, tmp);
				continue;
			}

			List<String> ls = new LinkedList<String>();
			if(req.containsKey(requestNum)){
				ls = req.get(requestNum);
			}
			ls.add(name);
			req.put(requestNum, ls);
		}


		for (int i=0; i<layout.length;i++){
			for (int j=0; j<layout[i].length;j++){

				if(layout[i][j]==0){
					continue;
				} 

				int target = layout[i][j];

				for ( ;target>0; target--){

					List<Integer> keySet = constructArray(req);
					List<List<Integer>> check =  helper(keySet, target); 

					if (check.size()>0){

						List<Integer> resultant = check.get(0);

						for(int allocated : resultant){
							List<String> temp = req.get(allocated);
							int rownum=i+1;
							int secnum=j+1;
							result.put(temp.get(0), "Row "+ rownum +" Section "+secnum);
							temp.remove(0);
							if(temp.size()>0){
								req.put(allocated, temp);
							} else {
								req.remove(allocated);
							}
							currentCapacity -= allocated;

						}
						break;
					}

				}
			} 
		}


		for (int i : req.keySet()) {

			List<String> temp = req.get(i);
			for(String s: temp) {

				if(currentCapacity >= i){
					result.put(temp.get(0), "Please Call to Split!");
					currentCapacity -= i;
				}
				else {

					result.put(temp.get(0),"Sorry we cant handle ur party");

				}
			}

		}

		for (int i : tempHold.keySet()) {
			List<String> temp = tempHold.get(i);
			for(String s: temp) {
				if(currentCapacity >= i){
					result.put(temp.get(0), "Please Call to Split!");
					currentCapacity -= i;
				}
				else {
					result.put(temp.get(0),"Sorry we cant handle ur party");
				}
			}
		}

		for(String str : requestList){
			String[] splitted = str.split("\\s+");
			String name = splitted[0];
			System.out.println(name + " - " + result.get(name));
		}
	}

	public List<Integer> constructArray(HashMap<Integer, List<String>> hm){

		List<Integer> res = new LinkedList<Integer>();

		for(Integer key : hm.keySet()){
			int size = hm.get(key).size();
			for( ;size>0;size--){
				res.add(key);
			}
		}
		return res;
	} 



	public List<List<Integer>> helper(List<Integer> candidates, int target) {

		List<Integer> list = new LinkedList<Integer>();
		List<List<Integer>> Lists = new LinkedList<List<Integer>>();
		List<List<Integer>> res = new LinkedList<List<Integer>>();
		int maxlen=0;
		Collections.sort(candidates);
		Lists = helper2(candidates,target,0,Lists,list,0);
		HashSet<List<Integer>> hs = new HashSet<List<Integer>>();
		for(List<Integer> al : Lists  ){

			if(!hs.contains(al) && al.size()>maxlen){

				maxlen= al.size();
				hs.add(al);
				res.add(al);

			}
		}
		return res;
	}

	public List<List<Integer>> helper2(List<Integer> candidates, int target, int sum, List<List<Integer>> Lists,List<Integer> list,int startIndex){

		if(sum==target){
			List<Integer> res = new LinkedList<Integer>();
			res.addAll(list);
			Collections.sort(res);
			Lists.add(res);
		}


		for(int i=startIndex;i<candidates.size();i++){
			if(sum+candidates.get(i)>target){
				return Lists;
			}
			int addition = candidates.get(i);
			sum = sum+candidates.get(i);
			int length = list.size();
			list.add(candidates.get(i));
			helper2(candidates,target,sum,Lists,list,i+1);
			list.remove(length);
			sum=sum-addition;


		}
		return Lists;

	}


}



