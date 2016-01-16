import React from 'react';
import {Input} from 'react-bootstrap';

const ResourceTypeDirectory = React.createClass({
    getInitialState: function() {
        return {directoryPath: this.props.resource.directoryPath};
    },
    handleChange: function(event) {
        this.setState({directoryPath: event.target.value});
    },
    componentWillReceiveProps: function(newProps) {
      this.setState({directoryPath: newProps.resource.directoryPath});
    },
    render() {
        return (
          <div className="form-group">
              <label>Directory Path</label>
              <input type="text" className="form-control"
                     value={this.state.directoryPath}  onChange={this.handleChange}
                     placeholder="Directory Path"></input>
          </div>
        );
    }
});

export default ResourceTypeDirectory;
