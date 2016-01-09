

export function elasticSearchHost() {
	var config = getConfig()
	var host = config.elasticSearchHost+":"+config.elasticSearchPort
  	return {host: host, log: 'error'}
}


function getConfig() {
  return window["config"];
}
