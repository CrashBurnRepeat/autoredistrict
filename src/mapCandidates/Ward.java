package mapCandidates;
import java.util.*;

import serialization.*;

public class Ward extends ReflectionJSONObject<Ward> {
    public int id;
	public static int id_enumerator = 0;
	public int state = 0;
	public int temp = -1;
	public double area = 0;
	
	//public String name = "";


    public Vector<Edge> edges = new Vector<Edge>();
    public Vector<Ward> neighbors = new Vector<Ward>();
    public double[] neighbor_lengths;
    public Vector<Demographic> demographics = new Vector<Demographic>();
    private double[][] mu_sigma_n = null;
    
    public boolean has_census_results = false;
    public boolean has_election_results = false;
    public double population=1;
    public void recalcMuSigmaN() {
    	try {
   		double[] successes = new double[Candidate.candidates.size()];
		double total = 0;
        for( Demographic d : demographics) {
            for( int j = 0; j < d.vote_prob.length; j++) {
            	double n = d.population * d.vote_prob[j]*d.turnout_probability;
            	n /= Settings.voting_coalition_size;
            	total += n;
            	successes[j] += n;
            }
        }
        mu_sigma_n = new double[Candidate.candidates.size()][];
        for( int j = 0; j < successes.length; j++) {
        	double n = total;
        	double p = successes[j] / total;
			double mu = n*p;
			double sigma = n*p*(1-p);
        	mu_sigma_n[j] = new double[]{mu,sigma,n};
        	if( total == 0) {
        		mu_sigma_n[j] = new double[]{0,0,0};
        	}
        }   
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public double[][] getMuSigmaN() {
    	if( mu_sigma_n == null) {
    		recalcMuSigmaN();    		
    	}
    	return mu_sigma_n;
    }
    
    
	double[][] outcomes;

    public Ward() {
    	super();
    	id = id_enumerator++;
    }
    public boolean equals(Ward b) {
    	return b != null && b.id == this.id;
    }
    public void syncNeighbors() {
		for(Ward b : neighbors) {
			boolean is_in = false;
			for(Ward b2 : b.neighbors) {
				if( b2.id == this.id){
					is_in = true;
					break;
				}
			}
			if( !is_in) {
				b.neighbors.add(this);
			}
		}
    	
    }
    public void collectNeighborLengths() {
    	neighbor_lengths = new double[neighbors.size()];
    	for( int i = 0; i < neighbor_lengths.length; i++) {
    		neighbor_lengths[i] = 0;
    	}
		for(Edge e : edges) {
	    	for( int i = 0; i < neighbor_lengths.length; i++) {
	    		Ward b = neighbors.get(i);
	    		if( e.ward1_id == b.id || e.ward2_id == b.id){
	    			neighbor_lengths[i] += e.length;	
	    		}
	    	}
		}
    	
    }
    
    public void collectNeighbors() {
		//HashSet<ward> hashwards = new HashSet<ward>(); 
		neighbors = new Vector<Ward>();
		//System.out.println("edges: "+edges.size());
		//System.out.print("ward "+id+" neighbors: ");
		for( Edge e : edges) {
			Ward b = e.ward1.id == this.id ? e.ward2 : e.ward1;
			if( b != null && b.id != this.id) {
				boolean is_in = false;
				for(Ward b2 : neighbors) {
					if( b2.id == b.id){
						is_in = true;
						break;
					}
				}
				if( !is_in) {
					neighbors.add(b);
					//System.out.print(""+b.id+", ");
				}
			}
		}
		//System.out.println();
    }

    
	@Override
	public void post_deserialize() {
		super.post_deserialize();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pre_serialize() {
		super.pre_serialize();
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject instantiateObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
    double[] getOutcome() {
    	if( outcomes == null) {
    		generateOutComes();
    	}
    	int i = (int)Math.floor(Math.random()*(double)outcomes.length);
    	return outcomes[i];
    }
    
    public void generateOutComes() {
    	outcomes = new double[Settings.num_ward_outcomes][];
    	
        //aggregate and normalize voting probs
    	double[] probs = new double[Candidate.candidates.size()];
        for(int i = 0; i < probs.length; i++) {
        	probs[i] = 0;
        }
        for( Demographic d : demographics) {
            for( int j = 0; j < d.vote_prob.length; j++) {
            	probs[j] += d.population * d.vote_prob[j]*d.turnout_probability;
            }
        }
        double total_population = 0;
        for(int i = 0; i < probs.length; i++) {
        	total_population += probs[i];
        }
        double r_tot_prob  = 1.0/total_population;
        for(int i = 0; i < probs.length; i++) {
        	probs[i] *= r_tot_prob;
        }

    	for( int i = 0; i < outcomes.length; i++) {
    		outcomes[i] = new double[probs.length];
            for(int j = 0; j < total_population; j++) {
                double p = Math.random();
                for( int k = 0; k < probs.length; k++) {
                    p -=  probs[k];
                    if( p <= 0) {
                    	outcomes[i][k]++;
                        break;
                    }
                }
    		}
    	}
    }
}
