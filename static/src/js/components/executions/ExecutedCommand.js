import React from 'react';
import moment from 'moment';

const ExecutedCommand = React.createClass({
  render() {
    return (
    
        <div className="box">
            <div className="box-header with-border">
              <i className="fa fa-text-width"></i>
              <h3 className="box-title">Executed Command</h3>
            </div>
             <div className="box-body">
              <p>ansible-playbook -i ../development.ini webservers.yml</p>
            </div>
           </div>

    );
  }
});

export default ExecutedCommand;
