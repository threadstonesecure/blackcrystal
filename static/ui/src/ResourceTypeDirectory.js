import React from 'react';
import {Input} from 'react-bootstrap';

const ResourceTypeDirectory = React.createClass({
    getInitialState: function() {
        return {directoryPath: ''};
    },
    handleChange: function(event) {
        this.setState({directoryPath: event.target.value});
    },
    render() {
        return (
            <div>
                <Input type="text" label="Directory Path" onChange={this.handleChange}
                       placeholder="Enter the directory path of your ansible-project on host"/>
            </div>
        );
    }
});

export default ResourceTypeDirectory;
