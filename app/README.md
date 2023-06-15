# Monolith Clean Architecture 

# Main Modules 
- Network Layer - Host the api 
- Data Layer - Hold repositories which responsibility for data of the app
- Domain Layer - Use case and business driven 
- Presentation Layer - View models 

# Others Modules
- klt-common : Common code for kotlin
- common : Common code for android-app

# Architectures 

- MVI 
- MVVM 

# Tech stack:




# Testing stacks:
- JUnit / 
- Mockk.io - mocking framework (can use Mockito as well) `coEvery { datasource.getLegoTheme() } returns mockResult`
- Turbine - a small testing library for kotlinx.coroutines Flow. ` flow.test { .... }`
- Truth - verification lib `Truth.assertThat(result).isInstanceOf(DataResult.Error::class.java)`

# TODO:
- [ ] Unit test for data and network layer 
- [ ] Room DB demonstrate with unit test 
- [ ] MVI with jetpack compose
