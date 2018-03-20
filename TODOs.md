
### TODOs

I am sure I am forgetting a bunch of things but the below should be decent start

**APP:**
1. DBAccess class is directly returning json objects. Should return models and the api layer should be massaging the data to create desired objects
2. For running in localhost, the db parameters are hardcoded, this should be config driven
3. Inputs should be validated everywhere, especially the date fields
4. Appropriate auth headers should be mandated for all api calls
5. API versioning scheme should be in place for backward compatibilities
6. Logging should be implemented and not sys outs
7. Unit tests should be added using mock db calls

**BUILD:**
1. Build should not be adding .war file in tomcat image
2. Ideally, all 3rd party images should not be tampered with. The build should create a separate image just for our app
3. Build info should be persisted somewhere with all relevant metadata
4. Build should also create startup/shutdown scripts that get executed on endpoints that do the actual task on bringing up containers
5. There should be approvals logic in place before deploying to production
6. If something fails during prod deploy, auto rollbacks should be implemented
7. Prod deploy should support multiple deploy strategies. Eg: one box at a time, etc.
8. Some mechanism to let LB know to NOT send new traffic to boxes that are about to be taken down for deploy
9. Some sleeping mechanism in place to ensure existing LB connections are not chopped right away
10. Ideally, steps 5 to 8 should be offloaded to a deployment system
11. Build system should only focus on creating the bundles (and call deploy apis if CD is needed)
12. There should be an in-house docker hub registry

**DOCKER:**
1. DB credentials should not be exposed in docker file
2. MySQL should ideally be deployed on bare metal. If we must use container, then correct volumes should be set up

**ENDPOINTS:**
1. The code uses hardcoded IPs from Digital Ocean for test and deploy
2. Ideally, the app cluster should be configured during app creation with following capabilities
- Configure the AZs the app is expected to be deployed on
- Configure the framework for the app (java, node, ruby, etc.)
- Provision the desired number of computes
- Create LB models (VIPs, POOLs, Routing, etc.)
- Support Flexups and flexdowns
- Onboard new AZs or Decommission existing AZs
- Again, steps 2.3 to 2.6 should be offloaded to a dedicated provisioning system
- Deciding on the cloud providers can be abstracted out from the clients

**LOGGING/MONITORING:**
1. Inbuilt framework that does logging to a central log server for the app
2. Inbuilt framework that reports metrics of endpoints
3. Some sort of aggregation server that converts raw metrics into consumable/actionable data	


 
