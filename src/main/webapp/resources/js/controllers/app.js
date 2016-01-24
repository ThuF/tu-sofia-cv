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

	$scope.projects = [{
		'projectId': 1,
		'name': 'Round Icons',
		'category': 'Graphic Design',
		'icon': '../../resources/img/portfolio/roundicons.png',
		'previewImage': '../../resources/img/portfolio/roundicons-preview.png',
		'description': 'Use this area to describe your project. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est blanditiis dolorem culpa incidunt minus dignissimos deserunt repellat aperiam quasi sunt officia expedita beatae cupiditate, maiores repudiandae, nostrum, reiciendis facere nemo!'
	}, {
		'projectId': 2,
		'name': 'Startup Framework',
		'category': 'Website Design',
		'icon': '../../resources/img/portfolio/startup-framework.png',
		'previewImage': '../../resources/img/portfolio/startup-framework-preview.png',
		'description': 'Use this area to describe your project. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est blanditiis dolorem culpa incidunt minus dignissimos deserunt repellat aperiam quasi sunt officia expedita beatae cupiditate, maiores repudiandae, nostrum, reiciendis facere nemo!'
	}, {
		'projectId': 3,
		'name': 'Treehouse',
		'category': 'Website Design',
		'icon': '../../resources/img/portfolio/treehouse.png',
		'previewImage': '../../resources/img/portfolio/treehouse-preview.png',
		'description': 'Use this area to describe your project. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est blanditiis dolorem culpa incidunt minus dignissimos deserunt repellat aperiam quasi sunt officia expedita beatae cupiditate, maiores repudiandae, nostrum, reiciendis facere nemo!'
	}, {
		'projectId': 4,
		'name': 'Golden',
		'category': 'Website Design',
		'icon': '../../resources/img/portfolio/golden.png',
		'previewImage': '../../resources/img/portfolio/golden-preview.png',
		'description': 'Use this area to describe your project. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est blanditiis dolorem culpa incidunt minus dignissimos deserunt repellat aperiam quasi sunt officia expedita beatae cupiditate, maiores repudiandae, nostrum, reiciendis facere nemo!'
	}, {
		'projectId': 5,
		'name': 'Escape',
		'category': 'Website Design',
		'icon': '../../resources/img/portfolio/escape.png',
		'previewImage': '../../resources/img/portfolio/escape-preview.png',
		'description': 'Use this area to describe your project. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est blanditiis dolorem culpa incidunt minus dignissimos deserunt repellat aperiam quasi sunt officia expedita beatae cupiditate, maiores repudiandae, nostrum, reiciendis facere nemo!'
	}, {
		'projectId': 6,
		'name': 'Dreams',
		'category': 'Website Design',
		'icon': '../../resources/img/portfolio/dreams.png',
		'previewImage': '../../resources/img/portfolio/dreams-preview.png',
		'description': 'Use this area to describe your project. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est blanditiis dolorem culpa incidunt minus dignissimos deserunt repellat aperiam quasi sunt officia expedita beatae cupiditate, maiores repudiandae, nostrum, reiciendis facere nemo!'
	}];

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