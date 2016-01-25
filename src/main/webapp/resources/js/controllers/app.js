var app = angular.module('cv', []);

app.controller('CVController', function($scope, $http) {

	$http.get('../../api/v1/public/roles/admin').success(function(data) {
		$scope.isAdmin = data;
	});

	$http.get('../../api/v1/public/personal-info').success(function(data) {
		$scope.personalInfo = data;
	});

	$http.get('../../api/v1/public/skills/count').success(function(data) {
		$scope.skillsCount = data;
	});

	$http.get('../../api/v1/public/skills/groups').success(function(data) {
		$scope.skillGroups = data;
	});

	$http.get('../../api/v1/public/projects/count').success(function(data) {
		$scope.projectsCount = data;
	});

	$http.get('../../api/v1/public/projects').success(function(data) {
		$scope.projects = data;
	});

	$http.get('../../api/v1/public/timeline/count').success(function(data) {
		$scope.timelineCount = data;
	});

	$http.get('../../api/v1/public/timeline').success(function(data) {
		$scope.timeline = data;
	});

});