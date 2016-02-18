/**
 ******************************************************************************
 *
 * This class implements the dictionary trie for the Boggle
 * application.  Objects of this class are used by the GUI
 * to handle text input.  The responses include returning
 * whether or not a word is valid.
 *
 *
 * @author Navyata Bawa
 * @date 02/15/2016
 *****************************************************************************/
/*****************************************************************************

  IMPLEMENT THIS CLASS

 *****************************************************************************/


import java.util.*;
import java.io.*;


public class DictionaryTrie implements TrieInterface
{
	/**
	 *  Creates a Trie object with the given dictionary.
	 *
	 *  @param dictionaryName the name of the dictionary file
	 */
	public int trie_size=0;
	private node root;
	public ArrayList<String> wordslist;
	public DictionaryTrie(String dictionaryName)
	{
		root = new node();
		wordslist = new ArrayList<>();
		String line = null;
		try {
			//FileReader reads text files in the default encoding.
			FileReader fileReader = 
				new FileReader(dictionaryName);

			//Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = 
				new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				//while there is some text in each line
				//add the string into arraylist
				if(line.length()>0)
				{
					//wordslist.add(line);
					add(line);
				}
			}   
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
					dictionaryName + "'"); 
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
					+ dictionaryName + "'");                  
		}
	}

	//define node for trie
	class node{
		node [] children;
		boolean marked;

		public node()
		{
			children = new node[26];
			for(int i=0;i<26;i++)
				{
					children[i] =null;
				}
			marked =false;

		}
	}

	/**
	 *  Prunes a the set of strings possible on a given
	 *  board by using BFS on the dictionary trie.
	 *
	 *  @param board the current game board
	 */

	ArrayList<String> allWords = new ArrayList<>();
	public void boardBFS(String[] board)
	{
		//board = new String []{"i","a", "p", "q", "i", "s", "l", "w", "t", "b", "a", "h", "y", "d", "a", "g"};
		allWords = new ArrayList<>();
		//check if each string is there in trie
		node n=root;
		for(int i=0;i<16;i++)
		{
			String allChars="";
			boolean done[] = new boolean[16];
			for(int dcnt =0;dcnt<16;dcnt++)
			{
				done[dcnt]=false;
			}
			done[i] = true;
			checkString(n,i,allChars,board,done);
		}

	}

	void checkString(node n,int i,String allChars, String[] board, boolean[] done)
	{	
		ArrayList<Integer> allNeighbours=new ArrayList<>();	
		int val = board[i].charAt(0) - (int)'a';
		if(n.children[val] != null)//if node exists
		{
			allChars = allChars + board[i];
			if(n.children[val].marked==true)
			{
				if(allChars.length()>2)
				{	
					boolean chk = valid(allChars);
					if(!chk){
						allWords.add(allChars);
					}
				}
			}
			allNeighbours=giveNeighbours(i, board,done);
			for (int object:allNeighbours)
			{
				done[object] = true;
				checkString(n.children[val],object,allChars, board,done);
				done[object]=false;
			}
		}
	}

	public ArrayList<Integer> giveNeighbours(int i, String[] board,boolean[] done)
	{
		ArrayList<Integer> neighbours=new ArrayList<>();
		if((i==0)||(i==3)||(i==12)||(i==15))//checks the 4 edges
		{
			if(i==0)
			{
				//don't check top, left top diag,right top diag,left, left bottom diag 
				if(done[i+1]!=true)
				{
					neighbours.add(i+1);
				}

				if(done[i+5]!=true)
				{
					neighbours.add(i+5);
				}

				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
			}

			else if(i==3)
			{
				//don't check top, left top diag,right top diag,right, right bottom diag
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
				if(done[i+3]!=true)
				{
					neighbours.add(i+3);
				}
				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
			}

			else if(i==12)
			{
				//don't check bottom, left top diag,right bottom diag,left, left bottom diag 
				if(done[i-4]!=true)
				{   
					neighbours.add(i-4);
				}   
				if(done[i-3]!=true)
				{   
					neighbours.add(i-3);
				}   
				if(done[i+1]!=true)
				{   
					neighbours.add(i+1);
				}   
			}

			else if(i==15)
			{
				if(done[i-5]!=true)
				{
					neighbours.add(i-5);
				}
				if(done[i-4]!=true)
				{
					neighbours.add(i-4);
				}
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
			}
		}

		if((i==1)||(i==2))//checks the upper 2 center edges
		{
			if(i==1)
			{
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
				if(done[i+1]!=true)
				{
					neighbours.add(i+1);
				}
				if(done[i+3]!=true)
				{
					neighbours.add(i+3);
				}
				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
				if(done[i+5]!=true)
				{
					neighbours.add(i+5);
				}

			}
			else if(i==2)
			{
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
				if(done[i+1]!=true)
				{
					neighbours.add(i+1);
				}
				if(done[i+3]!=true)
				{
					neighbours.add(i+3);
				}
				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
				if(done[i+5]!=true)
				{
					neighbours.add(i+5);
				}

			}
		}

		if((i==4)||(i==8))//checks the left 2 center edges
		{
			if(i==4)
			{
				if(done[i-4]!=true)
				{
					neighbours.add(i-4);
				}
				if(done[i+1]!=true)
				{
					neighbours.add(i+1);
				}
				if(done[i-3]!=true)
				{
					neighbours.add(i-3);
				}
				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
				if(done[i+5]!=true)
				{
					neighbours.add(i+5);
				}
			}
			else if(i==8)
			{
				if(done[i-4]!=true)
				{
					neighbours.add(i-4);
				}
				if(done[i+1]!=true)
				{
					neighbours.add(i+1);
				}
				if(done[i-3]!=true)
				{
					neighbours.add(i-3);
				}
				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
				if(done[i+5]!=true)
				{
					neighbours.add(i+5);
				}
			}
		}

		if((i==7)||(i==11))//checks the right 2 center edges
		{
			if(i==7)
			{
				if(done[i-5]!=true)
				{
					neighbours.add(i-5);
				}
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
				if(done[i+3]!=true)
				{
					neighbours.add(i+3);
				}
				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
				if(done[i-4]!=true)
				{
					neighbours.add(i-4);
				}


			}
			else if(i==11)
			{
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
				if(done[i-5]!=true)
				{
					neighbours.add(i-5);
				}
				if(done[i+3]!=true)
				{
					neighbours.add(i+3);
				}
				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
				if(done[i-4]!=true)
				{
					neighbours.add(i-4);
				}

			}
		}

		if((i==13)||(i==14))//checks the lower 2 center edges
		{
			if(i==13)
			{
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
				if(done[i+1]!=true)
				{
					neighbours.add(i+1);
				}
				if(done[i-3]!=true)
				{
					neighbours.add(i-3);
				}
				if(done[i-4]!=true)
				{
					neighbours.add(i-4);
				}
				if(done[i-5]!=true)
				{
					neighbours.add(i-5);
				}
			}
			else if(i==14)
			{
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
				if(done[i+1]!=true)
				{
					neighbours.add(i+1);
				}
				if(done[i-3]!=true)
				{
					neighbours.add(i-3);
				}
				if(done[i-4]!=true)
				{
					neighbours.add(i-4);
				}
				if(done[i-5]!=true)
				{
					neighbours.add(i-5);
				}
			}
		}

		else // center 4 blocks
		{
			if((i==5)||(i==6)||(i==9)||(i==10))
			{
				if(done[i-1]!=true)
				{
					neighbours.add(i-1);
				}
				if(done[i+1]!=true)
				{
					neighbours.add(i+1);
				}
				if(done[i-3]!=true)
				{
					neighbours.add(i-3);
				}
				if(done[i-4]!=true)
				{
					neighbours.add(i-4);
				}
				if(done[i-5]!=true)
				{
					neighbours.add(i-5);
				}
				if(done[i+3]!=true)
				{
					neighbours.add(i+3);
				}
				if(done[i+4]!=true)
				{
					neighbours.add(i+4);
				}
				if(done[i+5]!=true)
				{
					neighbours.add(i+5);
				}			
			}
		}

		return neighbours;
	}

	/**
	 * Inserts a string into the trie.
	 *
	 * @param s the string to check
	 */
	public void add(String s)
	{
		if(s.length()>0)
		{
			s = s.trim();
			s = s.toLowerCase();
		wordslist.add(s);
		node n = root;
		for(int j=0;j<s.length();j++)
		{
			char first = s.charAt(j);
			int i = first -'a';
			if(i>=0 && i<26 )
			{
			if(n.children[i] == null)
				{
				n.children[i] = new node ();
				}
			n = n.children[i];
			if(s.length()==(j+1))
				{
				n.marked = true;
				trie_size++;
				}
			} 
		}
		}
	}

	/**
	 *  Checks to see if a string is in the trie.  Also marks this
	 *  string for later use by the valid method.
	 *
	 *  @param s the string to check
	 *  @return true if the string is in the trie, false otherwise
	 */
	public boolean inDictionary(String s)
    {
            boolean flag=false;
             s = s.toLowerCase();
             node n =root;
             for(int i=0;i<s.length();i++)
             {
                    int pos = s.charAt(i) - 'a';
                    if(pos>=0 && pos<26)
                    {
                    if(n.children[pos] !=null)
                     {
                             n = n.children[pos];
                     }
                     else
                     {       flag = true;
                             break;
                     }
                    }
                    else
                    {
                            flag = true;
                    }
            }
            if(flag ==false)
             {
                     //System.out.println("Present");
                     if(n.marked == true)
                             {
                                     return true;
                             }
             }
            //}
            return false;
    }

	/**
	 *  Checks to see if a string is both in the trie
	 *  and a valid word on the game board.  This method is
	 *  only guaranteed to work after calling boardBFS.
	 *
	 *  @param s the string to check
	 *  @return true if the string is valid, false otherwise
	 */
	public boolean valid(String s)
	{
		s = s.toLowerCase();
		boolean flag= false;
		for(String object : allWords)
		{
			if(s.equals(object))
			{
				flag= true;
				break;
			}
		}
		return flag;
	}

	/**
	 *  Returns a collection of all the solutions for this
	 *  given board.
	 *  Only guaranteed to work after calling boardBFS.
	 *
	 *  @return all the solutions for this board
	 */
	public ArrayList<String> allSolutions()
	{
		for(String object: allWords)
		{
			object=object.toLowerCase();
		}
		return allWords;
	}

	/**
	 *  Returns the number of elements read into the trie from
	 *  the intial dictionary.
	 *
	 *  @return the size
	 */
	public int size()
	{
		return trie_size;
	}
}
