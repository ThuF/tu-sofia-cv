var app = angular.module('cv', []);

app.controller('CVController', function($scope, $http) {

	$scope.personalInfo = {
			'firstName': 'Yordan',
			'lastName': 'Pavlov',
			'headline': 'Software Developer @ SAP',
			'facebookProfileUrl': 'https://www.facebook.com/ThuF1',
			'twitterProfileUrl': 'https://twitter.com/ThuF1',
			'linkedinProfileUrl': 'https://www.linkedin.com/in/jordan-pavlov-ab3602107'
	};

	$scope.skills = [];

	$scope.skillGroups = [{
		'name': 'E-Commerce',
		'highlight': 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.',
		'icon': 'fa fa-shopping-cart fa-stack-1x fa-inverse'
	}, {
		'name': 'Responsive Design',
		'highlight': 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.',
		'icon': 'fa fa-laptop fa-stack-1x fa-inverse'
	}, {
		'name': 'Web Security',
		'highlight': 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.',
		'icon': 'fa fa-lock fa-stack-1x fa-inverse'
	}];

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