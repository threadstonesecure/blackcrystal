import React from 'react';
import {Input, MenuItem} from 'react-bootstrap';
import $ from 'jquery';
import ResourceTypeDirectory from './ResourceTypeDirectory'
import ResourceTypeGit from './ResourceTypeGit'

const ResourceSelector = React.createClass({

    loadData() {
        return $.getJSON("http://localhost:8080/resources");
    },
    getInitialState() {
        return {data: []};
    },

    onChange(event) {
        this.setState({selected: event.target.value});
    },

    componentDidMount(){
        this.loadData().success(function (data) {
            if (this.isMounted()) {
                this.setState({data: data});
            }
        }.bind(this))
    },
    render() {
        var defaultState = this.props.selected;
        console.log("defaultState:: "+defaultState)
        return (
            <div>
                <Input type="select" label="Resource" placeholder="Resource" onChange={this.onChange}>
                    <option value="" disabled selected hidden>Please Choose a Resource</option>
                    {
                        this.state.data.map(function (item) {
                                return <option value={item.name} selected={ (defaultState != null && defaultState == item.name) ? true : false}>
                                    {item.name}</option>
                            }
                        )}

                </Input>

            </div>
        );
    }
});

export default ResourceSelector;
