/*
    Adds Latex as a Fuel to the Compression Dynamo, generating 5000 RF.
*/

// <recipetype:thermal:compression_fuel>.addFuel(name as string, ingredient as CTFluidIngredient, energy as int);

<recipetype:thermal:compression_fuel>.addFuel("custom_compression_fuel", <fluid:thermal:latex>, 5000);

/*
    Removes Tree Oil as a fuel for the Compression Dynamo.
*/

// <recipetype:thermal:compression_fuel>.removeFuel(outputFluid as IFluidStack);

<recipetype:thermal:compression_fuel>.removeFuel(<fluid:thermal:tree_oil>);
