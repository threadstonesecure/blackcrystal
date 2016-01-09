

export function elasticSearchHost() {
	var config = getConfig()
	var host = config.elasticSearchHost+":"+config.elasticSearchPort
  	return {host: host, log: 'error'}
}

export function elasticSearchIndex() {
	//TODO get index name based on date (e.g. ansible_logs-2016.01.08)
  	return getConfig().elasticSearchIndex+"-*";
}


function getConfig() {
  return window["config"];
}
