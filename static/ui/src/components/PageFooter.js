import React from 'react';
import packageJSON from '../../package.json';

let version = packageJSON.version;

if (/docs/.test(version)) {
  version = version.split('-')[0];
}

const PageFooter = React.createClass({
  render() {
    return (
        <footer className="bs-docs-footer" role="contentinfo">
          <div className="container">
            
          </div>
        </footer>
      );
  }
});

export default PageFooter;
