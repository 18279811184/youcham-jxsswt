var vm;

var routes = [
  // Index page
  {
	name: 'home',
    path: '/',
    url: 'index.html',
    on: {
        pageAfterIn: function (e, page) {
          // do something after page gets into the view
        },
        pageInit: function (e, page) {
//        	vm = new Vue({
//        		el:'#page1',
//        		data:{
//        			list:[1,2,3,4,5]
//        		}
//        	});
        },
    }
  },
  {
	name: 'test',
    path: '/test/',
    url: 'searchbar.html',
    on: {
        pageAfterIn: function (e, page) {
          // do something after page gets into the view
        },
        pageInit: function (e, page) {
//        	vm = new Vue({
//        		el:'#page2',
//        		data:{
//        			list:[3,3,3,3,3]
//        		}
//        	});
        },
    }
  }
];
