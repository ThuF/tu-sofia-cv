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

	$scope.timeline = [{
		'title': 'Our Humble Beginnings',
		'startDate': '2009',
		'endDate': '2011',
		'icon': '../../resources/img/about/1.jpg',
		'description': 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!',
		'type': 'project'
	}, {
		'title': 'An Agency is Born',
		'startDate': 'March 2011',
		'icon': '../../resources/img/about/2.jpg',
		'description': 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!'
	}, {
		'title': 'Transition to Full Service',
		'startDate': 'December 2012',
		'icon': '../../resources/img/about/3.jpg',
		'description': 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!',
		'type': 'project'
	}, {
		'title': 'Phase Two Expansion',
		'startDate': 'July 2014',
		'icon': '../../resources/img/about/4.jpg',
		'description': 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt ut voluptatum eius sapiente, totam reiciendis temporibus qui quibusdam, recusandae sit vero unde, sed, incidunt et ea quo dolore laudantium consectetur!'
	}];
});