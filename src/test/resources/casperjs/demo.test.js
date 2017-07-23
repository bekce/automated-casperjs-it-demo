var sys = require('system');
//var casper = require('casper');
var url = sys.env.START_URL;
var siteName = 'Demo';

if (!url) {
  this.casper.die('START_URL not found');
}

var failures = [];

casper.test.on("fail", function(failure) {
  failures.push(failure);
});

// required in all test cases
casper.test.on("exit", function() {
  console.log("Exiting with status " + failures.length);
  casper.exit(failures.length);
});

casper.test.begin('Demo CasperIT test', function suite(test) {
  casper.start();

  //basic tests to see if we can reach the site etc.
  casper.thenOpen(url + "/", function(response) {
    console.log("Trying to open url: " + url);
  }).then( function() {
    test.assertHttpStatus(200, siteName + ' is up');
    test.assertTitle('Automated CasperJS Demo', siteName + ' has the correct title');
  });

  casper.run(function() {
    test.done();
  });

});
