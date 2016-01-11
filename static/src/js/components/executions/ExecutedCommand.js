import React from 'react';

const ExecutedCommand = React.createClass({
  render() {
    return (
      <div className="col-md-12">
        <div className="box">
          <div className="box-header with-border">
            <h3 className="box-title">Executed Command</h3>
          </div>
          <div className="box-body">
            <p>{this.props.command}</p>
          </div>
        </div>
      </div>
    );
  }
});

export default ExecutedCommand;
