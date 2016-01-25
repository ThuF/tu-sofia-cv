var cvApp = angular.module('cv', ['ngRoute']);

cvApp.config(function($routeProvider){
	$routeProvider.when('/personal-info', {
		controller: 'PersonalInfoController',
		templateUrl: 'templates/personal-info.html'
	}).when('/skills', {
		controller: 'SkillsController',
		templateUrl: 'templates/skills.html'
     }).when('/projects', {
		controller: 'ProjectsController',
		templateUrl: 'templates/projects.html'
     }).when('/positions', {
		controller: 'PositionsController',
		templateUrl: 'templates/positions.html'
     }).when('/educations', {
		controller: 'EducationsController',
		templateUrl: 'templates/educations.html'
     }).otherwise({
         redirectTo: '/personal-info'
     });
}).controller('PersonalInfoController', function($scope, $http) {
	const API = '../../../../api/v1/protected/admin/personal-info';

	loadModel();
	loadData();
	
	$scope.readonly = true;

	$scope.setReadonly = function(value) {
		setReadonly(value);
	}

	$scope.saveChanges = function() {
		if ($scope.isProfileInfoSet) {
			$http.put(API, $scope.data).success(function(data) {
				alert('The changes were successfully applied!');
				clearErrorMessage();
				setReadonly(true);
			}).error(function(errorMessage) {
				setErrorMessage(errorMessage.message);
			});
		} else {
			$http.post(API, $scope.data).success(function(data) {
				alert('The personal info was successfully added!');
				clearErrorMessage();
				setReadonly(true);
				loadData();
			}).error(function(errorMessage) {
				setErrorMessage(errorMessage.message);
			});
		}
	}

	$scope.cancelChanges = function() {
		clearErrorMessage();
		loadData();
		setReadonly(true);
	}

	function loadModel() {
		$http.get('resources/model-personal-info.json').success(function(data) {
			$scope.model = data;
		});
	}

	function loadData() {
		$http.get(API).success(function(data) {
			$scope.data = data;
			$scope.isProfileInfoSet = true;
		}).error(function(data) {
			$scope.isProfileInfoSet = false;
			$scope.data = {
					'firstName': null,
					'lastName': null,
					'headline': null,
					'facebookProfileUrl': null,
					'twitterProfileUrl': null,
					'linkedinProfileUrl': null
			}
		});
	}

	function setReadonly(value) {
		$scope.readonly = value;
	}

	function setErrorMessage(message) {
		$scope.errorMessage = message;
	}

	function clearErrorMessage() {
		setErrorMessage(null);
	}

}).controller('SkillsController', function($scope, $http) {
}).controller('ProjectsController', function($scope, $http) {
}).controller('PositionsController', function($scope, $http) {
}).controller('EducationsController', function($scope, $http) {
}).controller('MenuController', function($scope, $http) {
	$http.get('resources/menu.json').success(function(data) {
		$scope.menus = data;
    });
});