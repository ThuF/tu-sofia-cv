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
	var API = '../../../../api/v1/protected/admin/skills';

	$http.get('resources/model-skills.json').success(function(data) {
		$scope.model = data;
	});

	$http.get(API).success(function(data){
		$scope.data = data;
	});

	$scope.selectedEntry;
    $scope.newEntry = createEmptyEntry(); 
    $scope.errorMessage = null;

    $scope.showInfoForEntry = function(entry) {
		if (!$scope.operation) {
			if($scope.selectedEntry === entry){
				$scope.showEntry = false;
				$scope.selectedEntry = null;
				entry._selected_ = false;
			} else {
				for(var i = 0 ; i < $scope.data.length; i ++){
					$scope.data[i]._selected_ = false;
				}
				entry._selected_ = true;
				$scope.showEntry = true;
				$scope.selectedEntry = entry;
			}
		}
	};

	$scope.setOperation = function(operation) {
		switch (operation) {
			case 'new':
				if ($scope.operation !== 'new') {
					$scope.operation = 'new';
				} else {
					$scope.operation = null;
				}
				break;
			case 'update':
				if ($scope.operation !== 'update') {
					if ($scope.selectedEntry) {
						$scope.operation = 'update';
					} else {
						alert("Please first select entry for updated");
						$scope.operation = null;
					}
				} else {
					$scope.operation = null;
				}
				break;
			case 'delete':
				if ($scope.operation !== 'delete') {
					$scope.operation = 'delete';
				} else {
					$scope.operation = null;
				}
				break;
			default:
				$scope.operation = null;
				break;
		}
    };
           
    $scope.confirmAction = function() {
        switch ($scope.operation) {
            case 'new':
                newEntry($scope.newEntry);
                break;
            case 'update':
                updateEntry($scope.selectedEntry);
                break;
        }
    };

    $scope.cancelAction = function() {
        refreshData();
    };

   $scope.delete = function() {
	   if ($scope.selectedEntry) {
         	var confirmed = confirm('Do you realy want to delete the selected entry?');
           	if (confirmed) {
               	delete $scope.selectedEntry._selected_;
                   deleteEntry($scope.selectedEntry);
                   $scope.operation = null;
           	}                    	
       } else {
           alert('Please select row from the table.');
       }
   };
            
	function newEntry(entry) {
		delete $scope.newEntry._selected_;
		$http.post(API, entry).success(function() {
			refreshData();
			$scope.selectedEntry = null;
            $scope.operation = null;
            $scope.newEntry = createEmptyEntry();
            $scope.errorMessage = null;
		}).error(function(response) {
			$scope.errorMessage = response.message;
		});
	}
	
	function updateEntry(entry) {
		delete $scope.selectedEntry._selected_;
		$http.put(API + '/' + entry.skillId, entry).success(function() {
			refreshData();
            $scope.operation = null;
            $scope.errorMessage = null;
		}).error(function(response) {
			$scope.errorMessage = response.message;
		});
	}

	function deleteEntry(entry) {
		var deleteUrl = API + "/" + entry["skillId"];
		$http.delete(deleteUrl).success(function(){
			refreshData();
            $scope.selectedEntry = null;
			$scope.errorMessage = null;
		}).error(function(response){
			$scope.errorMessage = response.message;
		});
	}
            
	function refreshData() {
		$http.get(API).success(function(data){
			$scope.data = data;
        	$scope.newEntry = createEmptyEntry();
            $scope.selectedEntry = null;
            $scope.operation = null;
            $scope.errorMessage = null;
		}).error(function(response){
			$scope.errorMessage = response.message;
		});
	}

    function createEmptyEntry() {
    	return {
    		'skillId': null,
    		'name': null,
    		'isPublic': null
    	}
    }

}).controller('ProjectsController', function($scope, $http) {
}).controller('PositionsController', function($scope, $http) {
}).controller('EducationsController', function($scope, $http) {
}).controller('MenuController', function($scope, $http) {
	$http.get('resources/menu.json').success(function(data) {
		$scope.menus = data;
    });
});