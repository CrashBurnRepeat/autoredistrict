package mapCandidates;

import serializable.JSONObject;

public class Settings extends serializable.ReflectionJSONObject<Settings> {
    public static boolean mutate_to_neighbor_only = false;
    public static double species_fraction = 0.25;

    //spatial metrics
    public static double geometry_weight = 1;
    public static double population_balance_weight = 1;
    public static double disconnected_population_weight = 0; 

    //fairness metrics
    public static double disenfranchise_weight = 1;
    public static double voting_power_balance_weight = 1;
    
    public static double speciation_fraction;
    public static double replace_fraction;
    public static double mutation_rate;
    public static double mutation_boundary_rate;
    public static int trials;
    public static int population;
    
    /*
	@Override
	public void post_deserialize() {
		species_fraction = this.getDouble("species_fraction");
		geometry_weight = this.getDouble("geometry_weight");
		population_balance_weight = this.getDouble("population_balance_weight");
		disconnected_population_weight = this.getDouble("disconnected_population_weight");
		disenfranchise_weight = this.getDouble("disenfranchise_weight");
		voting_power_balance_weight = this.getDouble("voting_power_balance_weight");

		speciation_fraction = this.getDouble("speciation_fraction");
		replace_fraction = this.getDouble("replace_fraction");
		mutation_rate = this.getDouble("mutation_rate");
		mutation_boundary_rate = this.getDouble("mutation_boundary_rate");
		trials = (int)this.getDouble("trials");
		population = (int)this.getDouble("population");
	}
	@Override
	public void pre_serialize() {
		put("species_fraction",species_fraction);
		put("geometry_weight",geometry_weight);
		put("population_balance_weight",population_balance_weight);
		put("disconnected_population_weight",disconnected_population_weight);
		put("disenfranchise_weight",disenfranchise_weight);
		put("voting_power_balance_weight",voting_power_balance_weight);

		put("speciation_fraction",speciation_fraction);
		put("replace_fraction",replace_fraction);
		put("mutation_rate",mutation_rate);
		put("mutation_boundary_rate",mutation_boundary_rate);
		put("trials",trials);
		put("population",population);
	}
	@Override
	public JSONObject instantiateObject(String key) {
		return null;
	} */

}