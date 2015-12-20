angular.module('jobController', [])
	.controller('job', function($scope, $http) {
		 	$http.get('http://localhost:8080/jobs').success(function(data) {
       		$scope.jobs = data;
   		});
	});

