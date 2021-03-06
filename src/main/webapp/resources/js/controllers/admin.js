function getAction(oldAction, newAction, selectedEntry) {
	var action = null;
	switch (newAction) {
			case 'new':
				if (oldAction !== 'new') {
					action = 'new';
				}
				break;
			case 'update':
				if (oldAction !== 'update') {
					if (selectedEntry) {
						action = 'update';
					} else {
						alert("Please first select entry for updated");
					}
				}
				break;
			case 'delete':
				if (oldAction !== 'delete') {
					action = 'delete';
				}
				break;
		}
		return action;
}

function getEntryInfo(data, operation, currentEntry, selectedEntry) {
	var entry = undefined;
	if (!operation) {
		if (currentEntry !== selectedEntry) {
			for(var i = 0 ; i < data.length; i ++){
				data[i]._selected_ = false;
			}
			entry = currentEntry;
		} else {
			entry = null;
		}
	}
	return entry;
}

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
     }).when('/educations', {
		controller: 'EducationsController',
		templateUrl: 'templates/educations.html'
     }).when('/positions', {
		controller: 'PositionsController',
		templateUrl: 'templates/positions.html'
     }).when('/companies', {
		controller: 'CompaniesController',
		templateUrl: 'templates/companies.html'
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
	const PRIMARY_KEY_NAME = 'skillId';
	const API = '../../../../api/v1/protected/admin/skills';
	const API_MODEL = 'resources/model-skills.json';

	$http.get(API_MODEL).success(function(data) {
		$scope.model = data;
	});

	$http.get(API).success(function(data){
		$scope.data = data;
	});

	$scope.selectedEntry;
    $scope.newEntry = createEmptyEntry(); 
    $scope.errorMessage = null;

    $scope.showInfoForEntry = function(entry) {
    	var result = getEntryInfo($scope.data, $scope.operation, entry, $scope.selectedEntry);
    	if(result !== undefined) {
    		$scope.selectedEntry = result;
    		$scope.showEntry = result !== null;
			entry._selected_ = result !== null;
    	}
	};

	$scope.setOperation = function(operation) {
		$scope.operation = getAction($scope.operation, operation, $scope.selectedEntry);
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
		$http.put(API + '/' + entry[PRIMARY_KEY_NAME], entry).success(function() {
			refreshData();
            $scope.operation = null;
            $scope.errorMessage = null;
		}).error(function(response) {
			$scope.errorMessage = response.message;
		});
	}

	function deleteEntry(entry) {
		var deleteUrl = API + "/" + entry[PRIMARY_KEY_NAME];
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
    		'type': 'OTHER'
    	}
    }
}).controller('ProjectsController', function($scope, $http) {
	const PRIMARY_KEY_NAME = "projectId";
	const API = '../../../../api/v1/protected/admin/projects';
	const API_MODEL = 'resources/model-projects.json';

	$http.get(API_MODEL).success(function(data) {
		$scope.model = data;
	});

	$http.get(API).success(function(data){
		$scope.data = data;
	});

	$scope.selectedEntry;
    $scope.newEntry = createEmptyEntry(); 
    $scope.errorMessage = null;

    $scope.showInfoForEntry = function(entry) {
    	var result = getEntryInfo($scope.data, $scope.operation, entry, $scope.selectedEntry);
    	if(result !== undefined) {
    		$scope.selectedEntry = result;
    		$scope.showEntry = result !== null;
			entry._selected_ = result !== null;
    	}
	};

	$scope.setOperation = function(operation) {
		$scope.operation = getAction($scope.operation, operation, $scope.selectedEntry);
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
		$http.put(API + '/' + entry[PRIMARY_KEY_NAME], entry).success(function() {
			refreshData();
            $scope.operation = null;
            $scope.errorMessage = null;
		}).error(function(response) {
			$scope.errorMessage = response.message;
		});
	}

	function deleteEntry(entry) {
		var deleteUrl = API + "/" + entry[PRIMARY_KEY_NAME];
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
    		'projectId': null
    	}
    }
}).controller('EducationsController', function($scope, $http) {
	const PRIMARY_KEY_NAME = "educationId";
	const API = '../../../../api/v1/protected/admin/educations';
	const API_MODEL = 'resources/model-educations.json';

	$http.get(API_MODEL).success(function(data) {
		$scope.model = data;
	});

	$http.get(API).success(function(data){
		$scope.data = data;
	});

	$scope.selectedEntry;
    $scope.newEntry = createEmptyEntry(); 
    $scope.errorMessage = null;

    $scope.showInfoForEntry = function(entry) {
    	var result = getEntryInfo($scope.data, $scope.operation, entry, $scope.selectedEntry);
    	if(result !== undefined) {
    		$scope.selectedEntry = result;
    		$scope.showEntry = result !== null;
			entry._selected_ = result !== null;
    	}
	};

	$scope.setOperation = function(operation) {
		$scope.operation = getAction($scope.operation, operation, $scope.selectedEntry);
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
		$http.put(API + '/' + entry[PRIMARY_KEY_NAME], entry).success(function() {
			refreshData();
            $scope.operation = null;
            $scope.errorMessage = null;
		}).error(function(response) {
			$scope.errorMessage = response.message;
		});
	}

	function deleteEntry(entry) {
		var deleteUrl = API + "/" + entry[PRIMARY_KEY_NAME];
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
    		'educationId': null
    	}
    }
}).controller('PositionsController', function($scope, $http) {
	const PRIMARY_KEY_NAME = "positionId";
	const API = '../../../../api/v1/protected/admin/positions';
	const API_MODEL = 'resources/model-positions.json';
	const API_COMPANIES = '../../../../api/v1/protected/admin/companies';

	$http.get(API_MODEL).success(function(data) {
		$scope.model = data;
		$http.get(API_COMPANIES).success(function(data) {
			$scope.model.push({
					"id": "company",
					"name": "Company",
					"dropdown": []
			});
			for (var i = 0 ; i < data.length; i ++) {
				var company = data[i];
				$scope.model[5].dropdown.push({
					"label": company.name,
					"value": company
				})
			}
		});
	});

	$http.get(API).success(function(data){
		$scope.data = data;
	});

	$scope.selectedEntry;
    $scope.newEntry = createEmptyEntry(); 
    $scope.errorMessage = null;

    $scope.showInfoForEntry = function(entry) {
    	var result = getEntryInfo($scope.data, $scope.operation, entry, $scope.selectedEntry);
    	if(result !== undefined) {
    		$scope.selectedEntry = result;
    		$scope.showEntry = result !== null;
			entry._selected_ = result !== null;
    	}
	};

	$scope.setOperation = function(operation) {
		$scope.operation = getAction($scope.operation, operation, $scope.selectedEntry);
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
		$http.put(API + '/' + entry[PRIMARY_KEY_NAME], entry).success(function() {
			refreshData();
            $scope.operation = null;
            $scope.errorMessage = null;
		}).error(function(response) {
			$scope.errorMessage = response.message;
		});
	}

	function deleteEntry(entry) {
		var deleteUrl = API + "/" + entry[PRIMARY_KEY_NAME];
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
    		'positionId': null
    	}
    }
}).controller('CompaniesController', function($scope, $http) {
	const PRIMARY_KEY_NAME = "companyId";
	const API = '../../../../api/v1/protected/admin/companies';
	const API_MODEL = 'resources/model-companies.json';

	$http.get(API_MODEL).success(function(data) {
		$scope.model = data;
	});

	$http.get(API).success(function(data){
		$scope.data = data;
	});

	$scope.selectedEntry;
    $scope.newEntry = createEmptyEntry(); 
    $scope.errorMessage = null;

    $scope.showInfoForEntry = function(entry) {
    	var result = getEntryInfo($scope.data, $scope.operation, entry, $scope.selectedEntry);
    	if(result !== undefined) {
    		$scope.selectedEntry = result;
    		$scope.showEntry = result !== null;
			entry._selected_ = result !== null;
    	}
	};

	$scope.setOperation = function(operation) {
		$scope.operation = getAction($scope.operation, operation, $scope.selectedEntry);
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
		$http.put(API + '/' + entry[PRIMARY_KEY_NAME], entry).success(function() {
			refreshData();
            $scope.operation = null;
            $scope.errorMessage = null;
		}).error(function(response) {
			$scope.errorMessage = response.message;
		});
	}

	function deleteEntry(entry) {
		var deleteUrl = API + "/" + entry[PRIMARY_KEY_NAME];
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
    		'positionId': null
    	}
    }
}).controller('MenuController', function($scope, $http) {
	$http.get('resources/menu.json').success(function(data) {
		$scope.menus = data;
    });
});