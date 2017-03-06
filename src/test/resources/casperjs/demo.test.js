var sys = require('system');
var url = sys.env.START_URL;
var siteName = 'Demo';

if (!url) {
  this.casper.die('START_URL not found');
}

var failures = [];

casper.test.begin('Demo CasperIT test', function suite(test) {
  casper.start();

  casper.test.on("fail", function(failure) {
    failures.push(failure);
  });

  //basic tests to see if we can reach the site etc.
  casper.thenOpen(url + "/", function(response) {
    console.log("Trying to open url: " + url);
  }).then( function() {
    test.assertHttpStatus(200, siteName + ' is up');
    test.assertTitle('Automated CasperJS Demo', siteName + ' has the correct title');
  });

  casper.run(function() {
    this.exit(failures.length);
  });

});
