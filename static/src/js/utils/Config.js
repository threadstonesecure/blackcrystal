
export function elasticSearchHost() {
	var config = getConfig()
	var host = config.elasticSearchHost+":"+config.elasticSearchPort
  	return {host: host, log: 'error'}
}

export function elasticSearchIndex() {
	//TODO get index name based on date (e.g. ansible_logs-2016.01.08)
  	return getConfig().elasticSearchIndex+"-*";
}


export function resourcesTypesURI() {
  	return getBlackCrystalHost()+"/resource-types";
}

export function resourcesURI() {
  	return getBlackCrystalHost()+"/resources";
}


export function resourceURI() {
    return getBlackCrystalHost()+"/resource";
}

export function resourceDetailURI(name) {
	return resourceURI()+"/"+name;
}

export function jobsURI() {
  	return getBlackCrystalHost()+"/jobs";
}

export function jobURI() {
  	return getBlackCrystalHost()+"/job";
}

export function jobDetailURI(name) {
  	return jobURI()+"/"+name;
}

export function jobExecutionsURI(name,pageSize,page) {
    return jobDetailURI(name)+"/executions?size=" + pageSize + "&page=" + page;
}

export function jobExecutionURI(name,executionId) {
    return jobDetailURI(name)+"/execution/"+executionId;
}

export function jobExecutionLogURI(name,executionId) {
    return jobExecutionURI(name,executionId)+"/log";
}

function getBlackCrystalHost() {
  var config = getConfig()
  var host = "http://"+config.serverAddress+":"+config.serverPort;
  return host;
}

function getConfig() {
  return window["config"];
}
